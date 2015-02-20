package com.example.lauramarra.quintafeira;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Andamento extends ActionBarActivity {

    MyCustomAdapter dataAdapter = null;
    TextView textView, teste, titleText;
    MyDBHandler dbHandler;
    MyDBHandler2 dbChecker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_andamento);

        //textView = (TextView) findViewById(R.id.bdText);
        //teste = (TextView) findViewById(R.id.teste);
        titleText = (TextView) findViewById(R.id.titleText);

        titleText.setText("Controle aqui as materias cursadas e em andamento:");

        getFields();

        //Generate list View from ArrayList
        displayListView();

        //checkButtonClick();
    }

    private void displayListView() {

        //Array list of classes
        ArrayList<CheckMateria> materiaList = new ArrayList<CheckMateria>();
        CheckMateria itemList = new CheckMateria("MAC118","Calculo I",false);
        materiaList.add(itemList);
        itemList = new CheckMateria("FIT112","Fisica I",false);
        materiaList.add(itemList);
        itemList = new CheckMateria("EEL170","Computacao I",false);
        materiaList.add(itemList);
        /*
        itemList = new CheckMateria("3","Fisica Experimental I",false);
        materiaList.add(itemList);
        itemList = new CheckMateria("5","Quimica I",false);
        materiaList.add(itemList);
        */

        //create an ArrayAdaptar from the String Array
        dataAdapter = new MyCustomAdapter(this, R.layout.materia_info, materiaList);
        ListView listView = (ListView) findViewById(R.id.listView1);

        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                CheckMateria item = (CheckMateria) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),
                        "Clicked on Row: " + item.getName(),
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    public class MyCustomAdapter extends ArrayAdapter<CheckMateria> {

        private ArrayList<CheckMateria> listMaterias;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<CheckMateria> countryList) {
            super(context, textViewResourceId, countryList);
            this.listMaterias = new ArrayList<CheckMateria>();
            this.listMaterias.addAll(countryList);
        }

        private class ViewHolder {
            TextView code;
            CheckBox name;
            CheckBox classOk;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Context context = getContext();
            dbChecker = new MyDBHandler2(context, null, null, 1);
            final CheckMateria itemMat = listMaterias.get(position);

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));
            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.materia_info, null);


                holder = new ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.code);
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                holder.classOk = (CheckBox) convertView.findViewById(R.id.itemCheck);
                convertView.setTag(holder);

                final ViewHolder finalHolder = holder;
                final ViewHolder finalHolder1 = holder;

                holder.classOk.setOnClickListener( new View.OnClickListener() {

                    public void onClick(View v) {

                        final CheckBox cb = (CheckBox) v;

                        Toast.makeText(getApplicationContext(), itemMat.name + ": Cursando", Toast.LENGTH_SHORT).show();

                        if (cb.isChecked()) {
                            GradeParcialDB materia = new GradeParcialDB(itemMat.getCode(), itemMat.getName(), "Cursando");
                            dbChecker.addMateria(materia);
                        } else {
                            if (dbChecker.itemExists()) {
                                dbChecker.delMateria(itemMat.getCode());
                            }
                        }
                        //printDatabase();

                        finalHolder1.name.setOnClickListener( new View.OnClickListener() {
                            public void onClick(View v) {
                                CheckBox cbb = (CheckBox) v ;
                                //ItemMateria itemMateria = (ItemMateria) cb.getTag();
                                //itemMateria.setSelected(cb.isChecked());

                                Toast.makeText(getApplicationContext(), cbb.getText() + ": Concluída", Toast.LENGTH_SHORT).show();

                                if(cbb.isChecked()) {
                                    if(dbChecker.itemExists()){
                                        dbChecker.delMateria(itemMat.getCode());
                                    }
                                    GradeParcialDB materia = new GradeParcialDB(itemMat.getCode(), itemMat.getName(), "Concluida");
                                    dbChecker.addMateria(materia);
                                    cb.setEnabled(false);

                                    SharedPreferences settings = getSharedPreferences("preferences",0);
                                    settings.edit().putBoolean("check",true).commit();

                                }
                                else{
                                    if(dbChecker.itemExists()){
                                        dbChecker.delMateria(itemMat.getCode());
                                    }
                                    GradeParcialDB materia = new GradeParcialDB(itemMat.getCode(), itemMat.getName(), "Cursando");
                                    dbChecker.addMateria(materia);
                                    cb.setEnabled(true);
                                }
                                //printDatabase();
                            }
                        });
                    }

                });
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.code.setText(" (" +  itemMat.getCode() + ")");
            holder.name.setText(itemMat.getName());
            holder.name.setChecked(itemMat.isSelected());
            holder.name.setTag(itemMat);

            return convertView;
        }
    }

    public void getFields() {

        dbHandler = new MyDBHandler(Andamento.this, null, null, 1);

        String codigo, nome, periodo, creditos, preRequisito_1, preRequisito_2, preRequisito_3, diaSemana;
        GradeCompletaDB gradeCompletaDB;

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("123.txt")));

            // do reading, usually loop until end of file reading
            String mLine = reader.readLine();
            while (mLine != null) {
                //process line
                //titleText.setText(mLine);

                String[] message = mLine.split(";");

                codigo = message[0];
                nome = message[1];
                periodo = message[2];
                creditos = message[3];
                preRequisito_1 = message[4];
                preRequisito_2 = message[5];
                preRequisito_3 = message[6];

                diaSemana = message[7];

                gradeCompletaDB = new GradeCompletaDB(codigo, nome, periodo, creditos, preRequisito_1, preRequisito_2, preRequisito_3, diaSemana);
                dbHandler.addMateria(gradeCompletaDB);

                //printDatabase();

                mLine = reader.readLine();
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
    }

    public void printDatabase(){
        //String dbString = dbHandler.databaseToString();
        String dbString = dbChecker.databaseToString();
        textView.setText(dbString);
    }

/*
    public void printDatabase(){
        String dbString = dbHandler.databaseToString();
        textView.setText(dbString);
    }


    private void checkButtonClick() {
        Button myButton = (Button) findViewById(R.id.findSelected);
        myButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                StringBuffer responseText = new StringBuffer();
                responseText.append("The following were selected...\n");

                ArrayList<ItemMateria> countryList = dataAdapter.listMaterias;
                for(int i=0;i<countryList.size();i++){
                    ItemMateria country = countryList.get(i);
                    if(country.isSelected()){
                        responseText.append("\n" + country.getName());
                    }
                }

                Toast.makeText(getApplicationContext(),
                        responseText, Toast.LENGTH_LONG).show();

            }
        });

    }

/*
    TextView textView;
    MyDBHandler dbHandler;
    EditText codigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_andamento);

        codigo = (EditText) findViewById(R.id.codigo);
        textView = (TextView) findViewById(R.id.textView);

        getFields();
    }

    //Delete items
    public void deleteButtonClicked(View view){
        String inputText = codigo.getText().toString();
        dbHandler.delMateria(inputText);
        printDatabase();
        codigo.setText("");
    }
    */
}
