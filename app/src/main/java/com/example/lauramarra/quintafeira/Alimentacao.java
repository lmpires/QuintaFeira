package com.example.lauramarra.quintafeira;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Calendar;


public class Alimentacao extends ActionBarActivity {

    String[] menuOpAlimentacao = new String[] { "Restaurantes", "Qualidade", "Tempo de Espera"};
    private ListView menuAlimentacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disciplinas);

        menuAlimentacao = (ListView) findViewById(R.id.menuDisciplinas);

        ArrayAdapter<String> newAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuOpAlimentacao);

        menuAlimentacao.setAdapter(newAdapter);

        menuAlimentacao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Calendar sCalendar = Calendar.getInstance();
                Intent i;

                switch (position) {
                    case 0:
                        i = new Intent(getApplicationContext(), Restaurantes.class);
                        startActivity(i);
                        break;
                    case 1:
                        i = new Intent(getApplicationContext(), QualidadeRestaurante.class);
                        startActivity(i);
                        break;
                    case 2:
                        i = new Intent(getApplicationContext(), TempoEspera.class);
                        startActivity(i);
                        break;
                }
            }

        });
    }
}
