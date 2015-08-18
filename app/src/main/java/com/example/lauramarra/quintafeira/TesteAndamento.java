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
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class TesteAndamento extends ActionBarActivity {

    MyCustomAdapter dataAdapter = null;
    TextView textView, titleText;
    MyDBHandler dbHandler;
    MyDBHandler2 dbChecker;

    //create a variable of type SharedPreferences:
    SharedPreferences sharedpreferences, sharedprefString;
    String prename = "PREFERENCES";
    String prename2 = "PREFSTRING";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_andamento);

        titleText = (TextView) findViewById(R.id.titleText);
        titleText.setText("Controle aqui as materias cursadas e em andamento:");

        getFields();

        //Generate list View from ArrayList
        displayListView();
    }

    private void displayListView() {

        //Array list of classes
        final ArrayList<CheckMateria> materiaList = new ArrayList<CheckMateria>();

        String codigo, nome, diaSemana, status;
        CheckMateria itemList;

        String[] st;

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("123.txt")));

            // do reading, usually loop until end of file reading
            String mLine = reader.readLine();
            while (mLine != null) {

                String[] message = mLine.split(";");

                codigo = message[0];
                nome = message[1];
                diaSemana = message[7];
                status = message[9];

                st = diaSemana.split(":");

                //itemList = new CheckMateria(codigo, nome, diaSemana, false, status);
                //materiaList.add(itemList);

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

        //create an ArrayAdapter from the String Array
        dataAdapter = new MyCustomAdapter(this, R.layout.materia_info, materiaList);
        ListView listView = (ListView) findViewById(R.id.listView1);

        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);
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
            TextView status;
            CheckBox name;
            //Switch mySwitch;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            Context context = getContext();
            dbChecker = new MyDBHandler2(context, null, null, 1);
            final CheckMateria itemMat = listMaterias.get(position);

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            // get the SharedPreferences object
            sharedpreferences = getSharedPreferences(prename, Context.MODE_PRIVATE);
            sharedprefString = getSharedPreferences(prename2, Context.MODE_PRIVATE);

            // Store new primitive types in the shared preferences object
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

                //loadSavedPreferences(itemMat.getName());
                //loadPref(itemMat.getName());

                holder.name.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        final CheckBox cb = (CheckBox) v;
                        loadSavedPreferences(itemMat.getName());
                        loadPref(itemMat.getName());

                        if (cb.isChecked()) {
                            Toast.makeText(getApplicationContext(), cb.getText() + ": Cursando", Toast.LENGTH_LONG).show();

                            if (dbChecker.itemExists()) {
                                dbChecker.delMateria(itemMat.getCode());
                            }
                            GradeParcialDB materia = new GradeParcialDB(itemMat.getCode(), itemMat.getName(), itemMat.getDiaSemana(), "Cursando");
                            dbChecker.addMateria(materia);
                            editor2.putString(itemMat.getName(), "Cursando").apply();


                        } else {
                            Toast.makeText(getApplicationContext(), cb.getText() + ": Pendente", Toast.LENGTH_LONG).show();

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
                        AlertDialog.Builder builder = new AlertDialog.Builder(TesteAndamento.this);
                        builder.setTitle(itemMat.getName());
                        builder.setMessage("Gostaria de concluir essa disciplina?");

                        builder.setPositiveButton("Sim!", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                Toast.makeText(TesteAndamento.this, "concluida=" + arg1, Toast.LENGTH_SHORT).show();
                                if (dbChecker.itemExists()) {
                                    dbChecker.delMateria(itemMat.getCode());
                                }
                                GradeParcialDB materia = new GradeParcialDB(itemMat.getCode(), itemMat.getName(), itemMat.getDiaSemana(), "Concluida");
                                dbChecker.addMateria(materia);
                                itemMat.setStatus("Concluida");
                                status.setText("Concluida");
                                editor2.putString(itemMat.getName(), "Concluida").apply();

                            }
                        });

                        builder.setNeutralButton("Pendente", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                Toast.makeText(TesteAndamento.this, "Pendente=" + arg1, Toast.LENGTH_SHORT).show();

                                if (dbChecker.itemExists()) {
                                    dbChecker.delMateria(itemMat.getCode());
                                }
                                GradeParcialDB materia = new GradeParcialDB(itemMat.getCode(), itemMat.getName(), itemMat.getDiaSemana(), "Pendente");
                                dbChecker.addMateria(materia);
                                itemMat.setStatus("Pendente");
                                status.setText("Pendente");
                                editor2.putString(itemMat.getName(), "Pendente").apply();
                            }
                        });
/*
                        builder.setNegativeButton("Cursando", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                Toast.makeText(TesteAndamento.this, "cursando=" + arg1, Toast.LENGTH_SHORT).show();

                                if (dbChecker.itemExists()) {
                                    dbChecker.delMateria(itemMat.getCode());
                                }
                                GradeParcialDB materia = new GradeParcialDB(itemMat.getCode(), itemMat.getName(), itemMat.getDiaSemana(), "Cursando");
                                dbChecker.addMateria(materia);
                                itemMat.setStatus("cursando");
                                status.setText("cursando");
                                editor2.putString(itemMat.getName(),"Cursando").apply();
                            }
                        });
                        */
                        alertDialog = builder.create();
                        alertDialog.show();

                        //editor.putBoolean(itemMat.getName(), cb.isChecked()).apply();

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
    }

    public void getFields() {

        dbHandler = new MyDBHandler(TesteAndamento.this, null, null, 1);

        String codigo, nome, periodo, creditos, preRequisito_1, preRequisito_2, preRequisito_3, local, status;
        String diaSemana = null;
        String[] dia;
        GradeCompletaDB gradeCompletaDB;

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("123.txt")));

            // do reading, usually loop until end of file reading
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

                gradeCompletaDB = new GradeCompletaDB(codigo, nome, periodo, creditos, preRequisito_1, diaSemana, local, status);
                dbHandler.addMateria(gradeCompletaDB);

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
}

