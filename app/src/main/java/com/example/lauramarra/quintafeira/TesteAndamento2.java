package com.example.lauramarra.quintafeira;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class TesteAndamento2 extends ActionBarActivity implements AdapterView.OnItemSelectedListener {

    MyCustomAdapter dataAdapter = null;
    TextView textView, titleText;
    MyDBHandler dbHandler;
    MyDBHandler2 dbChecker;
    Spinner spinner;
    SharedPreferences sharedpreferences, sharedprefString, getSharedprefInt;
    String prename = "PREFERENCES";
    String prename2 = "PREFSTRING";
    String prename3 = "PREFINT";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_andamento2);

        titleText = (TextView) findViewById(R.id.titleText);
        titleText.setText("Controle aqui as materias cursadas e em andamento:");

        spinner = (Spinner) findViewById(R.id.spinner2);

        spinner.setOnItemSelectedListener(this);

        List<String> categories = new ArrayList<String>();
        categories.add("1o Periodo");
        categories.add("2o Periodo");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);

        getSharedprefInt = getSharedPreferences("spinner", Context.MODE_PRIVATE);
        spinner.setSelection(getSharedprefInt.getInt("spinner",0));

        getFields();

        displayListView(spinner.getSelectedItemPosition());
    }

    private void displayListView(int position) {

        loadPref2(spinner.getSelectedItem().toString());
        final ArrayList<CheckMateria> materiaList = new ArrayList<CheckMateria>();

        String codigo, nome, diaSemana, status, periodo;
        CheckMateria itemList;

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open("123.txt")));

            String mLine = reader.readLine();
            switch (position){
                case (0):
                    while (mLine != null) {
                        String[] message = mLine.split(";");
                        codigo = message[0];
                        nome = message[1];
                        periodo = message[2];
                        diaSemana = message[7];
                        status = message[9];

                        itemList = new CheckMateria(
                                codigo, nome, periodo, diaSemana, false, status);

                        if(periodo.equalsIgnoreCase("1"))
                            materiaList.add(itemList);

                        mLine = reader.readLine();
                    }
                    break;

                case (1):
                    while (mLine != null) {
                        String[] message = mLine.split(";");
                        codigo = message[0];
                        nome = message[1];
                        periodo = message[2];
                        diaSemana = message[7];
                        status = message[9];

                        itemList = new CheckMateria(
                                codigo, nome, periodo, diaSemana, false, status);

                        if(periodo.equalsIgnoreCase("2"))
                            materiaList.add(itemList);

                        mLine = reader.readLine();
                    }
                    break;
            }
        } catch (IOException e) {
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                }
            }
        }
        dataAdapter = new MyCustomAdapter(this, R.layout.materia_info, materiaList);
        ListView listView = (ListView) findViewById(R.id.listView1);
        listView.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        displayListView(spinner.getSelectedItemPosition());
        int selPosition = spinner.getSelectedItemPosition();
        SharedPreferences.Editor editor = getSharedprefInt.edit();
        editor.putInt("spinner", selPosition).apply();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void getFields() {

        dbHandler = new MyDBHandler(TesteAndamento2.this, null, null, 1);
        String codigo, nome, periodo, creditos,
                preRequisito_1, preRequisito_2, preRequisito_3, diaSemana, local, status;
        GradeCompletaDB gradeCompletaDB;

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("123.txt")));
            String mLine = reader.readLine();
            while (mLine != null) {
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
                status = message[9];

                gradeCompletaDB = new GradeCompletaDB(
                        codigo, nome, periodo, creditos,preRequisito_1,
                        diaSemana, local, status);
                dbHandler.addMateria(gradeCompletaDB);

                mLine = reader.readLine();
            }
        } catch (IOException e) {
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                }
            }
        }
    }

    private boolean loadSavedPreferences(String value) {
        SharedPreferences sharedPreferences = getSharedPreferences(prename, Context.MODE_PRIVATE);
        boolean checkBoxValue = sharedPreferences.getBoolean(value, false);
        if (checkBoxValue)
            return true;
        else
            return false;
    }

    private String loadPref(String value) {
        SharedPreferences sp = getSharedPreferences(prename2, Context.MODE_PRIVATE);
        String st = sp.getString(value, "Pendente");
        return st;
    }

    private int loadPref2(String value) {
        SharedPreferences sp = getSharedPreferences(prename3, Context.MODE_PRIVATE);
        int pos = sp.getInt(value, 0);
        return pos;
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

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            Context context = getContext();
            dbChecker = new MyDBHandler2(context, null, null, 1);
            final CheckMateria itemMat = listMaterias.get(position);

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            sharedpreferences = getSharedPreferences(prename, Context.MODE_PRIVATE);
            sharedprefString = getSharedPreferences(prename2, Context.MODE_PRIVATE);

            final SharedPreferences.Editor editor = sharedpreferences.edit();
            final SharedPreferences.Editor editor2 = sharedprefString.edit();

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.materia_info, null);

                holder = new ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.code);
                holder.status = (TextView) convertView.findViewById(R.id.status);
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                holder.status = (TextView) convertView.findViewById(R.id.status);

                convertView.setTag(holder);

                holder.name.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        final CheckBox cb = (CheckBox) v;
                        loadSavedPreferences(itemMat.getName());
                        loadPref(itemMat.getName());

                        if (cb.isChecked()) {
                            Toast.makeText(getApplicationContext(),
                                    cb.getText() + ": Cursando", Toast.LENGTH_LONG).show();

                            if (dbChecker.itemExists()) {
                                dbChecker.delMateria(itemMat.getCode());
                            }

                            GradeParcialDB materia = new GradeParcialDB(
                                    itemMat.getCode(), itemMat.getName(),
                                    itemMat.getDiaSemana(), "Cursando");

                            dbChecker.addMateria(materia);
                            editor2.putString(itemMat.getName(), "Cursando").apply();


                        } else {
                            Toast.makeText(getApplicationContext(),
                                    cb.getText() + ": Pendente", Toast.LENGTH_LONG).show();

                            if (dbChecker.itemExists()) {
                                dbChecker.delMateria(itemMat.getCode());
                            }
                        }
                        editor.putBoolean(itemMat.getName(), cb.isChecked()).apply();
                        editor2.putString(itemMat.getName(), "Pendente").apply();

                    }
                });

                holder.status.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {

                        final TextView status = (TextView) v;

                        loadSavedPreferences(itemMat.getName());

                        AlertDialog alertDialog;
                        AlertDialog.Builder builder = new AlertDialog.Builder(TesteAndamento2.this);
                        builder.setTitle(itemMat.getName());
                        builder.setMessage("Gostaria de concluir essa disciplina?");

                        builder.setPositiveButton("Sim!", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                Toast.makeText(TesteAndamento2.this,
                                        "concluida=" + arg1, Toast.LENGTH_SHORT).show();

                                if (dbChecker.itemExists()) {
                                    dbChecker.delMateria(itemMat.getCode());
                                }

                                GradeParcialDB materia = new GradeParcialDB(
                                        itemMat.getCode(), itemMat.getName(),
                                        itemMat.getDiaSemana(), "Concluida");

                                dbChecker.addMateria(materia);
                                itemMat.setStatus("Concluida");
                                status.setText("Concluida");
                                editor2.putString(itemMat.getName(), "Concluida").apply();

                            }
                        });

                        builder.setNeutralButton("Pendente", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                Toast.makeText(TesteAndamento2.this,
                                        "Pendente=" + arg1, Toast.LENGTH_SHORT).show();

                                if (dbChecker.itemExists()) {
                                    dbChecker.delMateria(itemMat.getCode());
                                }

                                GradeParcialDB materia = new GradeParcialDB(
                                        itemMat.getCode(), itemMat.getName(),
                                        itemMat.getDiaSemana(), "Pendente");

                                dbChecker.addMateria(materia);
                                itemMat.setStatus("Pendente");
                                status.setText("Pendente");
                                editor2.putString(itemMat.getName(), "Pendente").apply();
                            }
                        });
                        alertDialog = builder.create();
                        alertDialog.show();
                    }
                });

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.code.setText(" (" + itemMat.getCode() + ")");
            holder.name.setText(itemMat.getName());
            holder.name.setChecked(itemMat.isSelected());
            holder.name.setTag(itemMat);
            holder.status.setText(loadPref(itemMat.getName()));

            if (loadSavedPreferences(itemMat.getName()))
                holder.name.setChecked(true);
            return convertView;
        }
        private class ViewHolder {
            TextView code;
            TextView status;
            CheckBox name;
        }
    }
}

