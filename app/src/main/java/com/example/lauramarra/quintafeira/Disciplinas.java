package com.example.lauramarra.quintafeira;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;




public class Disciplinas extends ActionBarActivity {


    private ListView menuDisciplinas;

    String[] menuOpDisciplinas = new String[] { "Grade","Andamento", "Qualidade"};


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

                Intent i;
                switch (position) {
                    case 0:
                        i = new Intent(getApplicationContext(), Grade.class);
                        startActivity(i);
                        break;
                    case 1:
                        i = new Intent(getApplicationContext(), Andamento.class);
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
