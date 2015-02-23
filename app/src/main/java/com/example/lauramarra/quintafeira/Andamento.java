package com.example.lauramarra.quintafeira;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Andamento extends ActionBarActivity {

    MyCustomAdapter dataAdapter = null;
    TextView textView, titleText;
    MyDBHandler dbHandler;
    MyDBHandler2 dbChecker;
    Button nextAct;


    //create a variable of type SharedPreferences:
    SharedPreferences sharedpreferences;
    String prename="PREFERENCES";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_andamento);

        //textView = (TextView) findViewById(R.id.bdText);
        //teste = (TextView) findViewById(R.id.teste);
        nextAct = (Button) findViewById(R.id.nextACt);
        titleText = (TextView) findViewById(R.id.titleText);
        titleText.setText("Controle aqui as materias cursadas e em andamento:");

        getFields();

        //Generate list View from ArrayList
        displayListView();
    }

    private void displayListView() {

        //Array list of classes
        final ArrayList<CheckMateria> materiaList = new ArrayList<CheckMateria>();

        CheckMateria itemList = new CheckMateria("MAC118","Calculo I",false);
        materiaList.add(itemList);
        itemList = new CheckMateria("FIT112","Fisica I",false);
        materiaList.add(itemList);
        itemList = new CheckMateria("EEL170","Computacao I",false);
        materiaList.add(itemList);

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

        public MyCustomAdapter(Context context,
                               int textViewResourceId,
                               ArrayList<CheckMateria> countryList) {
            super(context, textViewResourceId, countryList);
            this.listMaterias = new ArrayList<CheckMateria>();
            this.listMaterias.addAll(countryList);
        }

        private class ViewHolder {
            TextView code;
            CheckBox name;
            Switch swt;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            Context context = getContext();
            dbChecker = new MyDBHandler2(context, null, null, 1);
            final CheckMateria itemMat = listMaterias.get(position);

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            // get the SharedPreferences object
            sharedpreferences= getSharedPreferences(prename , MODE_MULTI_PROCESS);

            // Store new primitive types in the shared preferences object
            final SharedPreferences.Editor editor=sharedpreferences.edit();


            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.materia_info, null);


                holder = new ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.code);
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                holder.swt  =(Switch) convertView.findViewById(R.id.switch1);

                convertView.setTag(holder);

                final ViewHolder finalHolder = holder;

                holder.name.setOnClickListener( new View.OnClickListener() {

                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        Toast.makeText(getApplicationContext(), cb.getText() + ": Cursando", Toast.LENGTH_LONG).show();

                        if(loadSavedPreferences(itemMat.getName()))
                            cb.setChecked(true);

                        if(cb.isChecked()) {
                            if(dbChecker.itemExists()){
                                dbChecker.delMateria(itemMat.getCode());
                            }
                            GradeParcialDB materia = new GradeParcialDB(itemMat.getCode(), itemMat.getName(), "Cursando");
                            dbChecker.addMateria(materia);
                        }
                        else{
                            if(dbChecker.itemExists()){
                                dbChecker.delMateria(itemMat.getCode());
                            }
                        }
                        editor.putBoolean(itemMat.getName(), cb.isChecked()).apply();
                    }
                });

                final ViewHolder finalHolder1 = holder;

                holder.swt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked) {
                            finalHolder1.name.setEnabled(false);

                            Toast.makeText(getApplicationContext(), finalHolder1.name.getText() + ": Concluída", Toast.LENGTH_SHORT).show();

                            if(finalHolder1.name.isChecked()) {
                                if(dbChecker.itemExists()){
                                    dbChecker.delMateria(itemMat.getCode());
                                }
                                GradeParcialDB materia = new GradeParcialDB(itemMat.getCode(), itemMat.getName(), "Concluida");
                                dbChecker.addMateria(materia);
                            }

                            else{
                                if(dbChecker.itemExists()){
                                    dbChecker.delMateria(itemMat.getCode());
                                }
                                GradeParcialDB materia = new GradeParcialDB(itemMat.getCode(), itemMat.getName(), "Cursando");
                                dbChecker.addMateria(materia);
                            }
                        }
                        if(!isChecked){
                            finalHolder.name.setEnabled(true);

                            Toast.makeText(getApplicationContext(), finalHolder1.name.getText() + "Cursando", Toast.LENGTH_SHORT).show();

                            finalHolder.name.setChecked(false);

                        }

                    }
                });

                nextAct.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent i;
                        i = new Intent(getApplicationContext(), TesteAcesso2.class);
                        startActivity(i);
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
            if(loadSavedPreferences(itemMat.getName()))
                holder.name.setChecked(true);

            return convertView;
        }
    }

    public void getFields() {

        dbHandler = new MyDBHandler(Andamento.this, null, null, 1);

        String codigo, nome, periodo, creditos, preRequisito_1, preRequisito_2, preRequisito_3, diaSemana, local;
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
                local = message[8];

                gradeCompletaDB = new GradeCompletaDB(codigo, nome, periodo, creditos, preRequisito_1, preRequisito_2, preRequisito_3, diaSemana, local);
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

    private boolean loadSavedPreferences(String value) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        boolean checkBoxValue = sharedPreferences.getBoolean(value, false);
        if (checkBoxValue)
            return true;
        else
            return false;
    }
}
