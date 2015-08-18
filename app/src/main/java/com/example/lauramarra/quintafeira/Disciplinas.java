package com.example.lauramarra.quintafeira;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Calendar;


public class Disciplinas extends ActionBarActivity {

    String[] menuOpDisciplinas = new String[] { "Grade","Andamento", "Qualidade"};
    private ListView menuDisciplinas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disciplinas);

        menuDisciplinas = (ListView) findViewById(R.id.menuDisciplinas);

        ArrayAdapter<String> newAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuOpDisciplinas);

        menuDisciplinas.setAdapter(newAdapter);
        menuDisciplinas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Calendar sCalendar = Calendar.getInstance();
                int day = sCalendar.get(Calendar.DAY_OF_WEEK);
                Intent i;

                switch (position) {
                    case 0:
                        switch (day) {
                            case Calendar.MONDAY:
                                i = new Intent(getApplicationContext(), Segunda.class);
                                startActivity(i);
                                break;
                            case Calendar.TUESDAY:
                                i = new Intent(getApplicationContext(), Terca.class);
                                startActivity(i);
                                break;
                            case Calendar.WEDNESDAY:
                                i = new Intent(getApplicationContext(), Wednesday.class);
                                startActivity(i);
                                break;
                            case Calendar.THURSDAY:
                                i = new Intent(getApplicationContext(), Thursday.class);
                                startActivity(i);
                                break;
                            case Calendar.FRIDAY:
                                i = new Intent(getApplicationContext(), Friday.class);
                                startActivity(i);
                                break;
                        }
                        break;
                    case 1:
                        i = new Intent(getApplicationContext(), TesteAndamento2.class);
                        startActivity(i);
                        break;
                    case 2:
                        i = new Intent(getApplicationContext(), Qualidade.class);
                        startActivity(i);
                        break;
                }
            }

        });
    }
}
