package com.example.lauramarra.quintafeira;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TempoEspera extends ActionBarActivity {

    TextView textView;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tempo_espera);

        TextView textView = (TextView)findViewById(R.id.textView);
        textView.setText("Colsulte o tempo de espera por restaurante:");

        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        expListView.setAdapter(listAdapter);

        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                switch (groupPosition){
                    case 0:
                        Toast.makeText(getApplicationContext(),
                                "Restaurantes da Escola Politécnica",
                                Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                Intent i;

                switch (groupPosition){
                    case 0:
                        switch(childPosition){
                            case 0:
                                i = new Intent(getApplicationContext(), Burguesao.class);
                                startActivity(i);
                                break;

                            case 1:
                                i = new Intent(getApplicationContext(), Burguesao.class);
                                startActivity(i);
                                break;

                            case 2:
                                i = new Intent(getApplicationContext(), Burguesao.class);
                                startActivity(i);
                                break;

                            default:
                                break;
                        }
                        break;

                    default:
                        break;
                }
                return false;
            }
        });
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Poli");
        listDataHeader.add("CCMN");
        listDataHeader.add("EBA");


        // Adding child data
        List<String> restPoli = new ArrayList<String>();
        restPoli.add("Burguesão");
        restPoli.add("Kilowatts");
        restPoli.add("Projectus");

        List<String> restCCMN = new ArrayList<String>();
        restCCMN.add("Desculpe, tempo de espera não disponível no momento.");

        List<String> restEBA = new ArrayList<String>();
        restEBA.add("Desculpe, tempo de espera não disponível no momento.");


        listDataChild.put(listDataHeader.get(0), restPoli);
        listDataChild.put(listDataHeader.get(1), restCCMN);
        listDataChild.put(listDataHeader.get(2), restEBA); // Header, Child data

    }
}
