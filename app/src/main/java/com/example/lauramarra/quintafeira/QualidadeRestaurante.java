package com.example.lauramarra.quintafeira;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class QualidadeRestaurante extends ActionBarActivity {

    TextView textView;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qualidade_restaurante);

        TextView textView = (TextView)findViewById(R.id.textView);
        textView.setText("Avalie aqui os restaurantes!");

        expListView = (ExpandableListView) findViewById(R.id.lvQRest);

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

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                Intent launchBrowser;
                Uri uriUrl;
                switch (groupPosition){
                    case 0:
                        switch(childPosition){
                            case 0:
                                uriUrl = Uri.parse("https://plus.google.com/107217033654580487496/posts/JHVUSuSDz7D");
                                launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                                startActivity(launchBrowser);
                                break;

                            case 1:
                                uriUrl = Uri.parse("https://plus.google.com/107217033654580487496/posts/gfQE2Y3wfwt");
                                launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                                startActivity(launchBrowser);
                                break;

                            case 2:
                                uriUrl = Uri.parse("https://plus.google.com/107217033654580487496/posts/RQNLMZKJEFk");
                                launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                                startActivity(launchBrowser);
                                break;
                            default:
                                Toast.makeText(getApplicationContext(),
                                        "Desculpe, avalição não disponível no momento.",
                                        Toast.LENGTH_SHORT).show();
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

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        listDataHeader.add("Poli");
        listDataHeader.add("CCMN");
        listDataHeader.add("EBA");

        List<String> restPoli = new ArrayList<String>();
        restPoli.add("Burguesão");
        restPoli.add("Kilowatts");
        restPoli.add("Projectus");

        List<String> restCCMN = new ArrayList<String>();
        restCCMN.add("Desculpe, cardápio não disponível no momento.");

        List<String> restEBA = new ArrayList<String>();
        restEBA.add("Desculpe, cardápio não disponível no momento.");

        listDataChild.put(listDataHeader.get(0), restPoli);
        listDataChild.put(listDataHeader.get(1), restCCMN);
        listDataChild.put(listDataHeader.get(2), restEBA);
    }
}
