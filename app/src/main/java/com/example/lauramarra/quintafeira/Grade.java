package com.example.lauramarra.quintafeira;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Grade extends ActionBarActivity {

    TextView textView;
    MyDBHandler dbHandler;
    EditText codigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);

        codigo = (EditText) findViewById(R.id.codigo);
        textView = (TextView) findViewById(R.id.textView);

        getFields();
    }

    public void getFields() {


        dbHandler = new MyDBHandler(this, null, null, 1);

        BufferedReader bufferedReader = null;
        String codigo, nome, periodo, creditos, preRequisito_1, preRequisito_2, preRequisito_3, diaSemana;
        Materia materia;

        try {
            String sCurrentLine = null;
            bufferedReader = new BufferedReader(new InputStreamReader(getAssets().open("123.txt")));

            while ((sCurrentLine = bufferedReader.readLine()) != null) {

                String[] message = sCurrentLine.split(",");

                codigo = message[0];
                nome = message[1];
                periodo = message[2];
                creditos = message[3];
                preRequisito_1 = message[4];
                preRequisito_2 = message[5];
                preRequisito_3 = message[6];
                diaSemana = message[7];

                materia = new Materia(codigo, nome, periodo, creditos, preRequisito_1, preRequisito_2, preRequisito_3, diaSemana);
                dbHandler.addMateria(materia);

                printDatabase();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) bufferedReader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void printDatabase(){
        String dbString = dbHandler.databaseToString();
        textView.setText(dbString);
    }

    //Delete items
    public void deleteButtonClicked(View view){
        String inputText = codigo.getText().toString();
        dbHandler.delMateria(inputText);
        printDatabase();
        codigo.setText("");
    }
}
