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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


public class Restaurantes extends ActionBarActivity {

    TextView textView;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurantes);

        TextView textView = (TextView)findViewById(R.id.textView);
        textView.setText("Colsulte o cardápio do dia:");

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

                Intent launchBrowser;
                Uri uriUrl;

                Calendar sCalendar = Calendar.getInstance();
                int day = sCalendar.get(Calendar.DAY_OF_WEEK);

                switch (groupPosition){
                    case 0:
                        switch(childPosition){
                            case 0:
                                switch (day){
                                    case Calendar.MONDAY:
                                        uriUrl = Uri.parse("https://drive.google.com/a/poli.ufrj.br/file/d/0B0Zmx65mhxBzMWp3YjNya3F2eDQ/view?usp=sharing");
                                        launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                                        startActivity(launchBrowser);
                                        break;
                                    case Calendar.TUESDAY:
                                        uriUrl = Uri.parse("https://drive.google.com/a/poli.ufrj.br/file/d/0B0Zmx65mhxBzOG5IOVI3YzVTYXM/view?usp=sharing");
                                        launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                                        startActivity(launchBrowser);
                                        break;
                                    case Calendar.WEDNESDAY:
                                        uriUrl = Uri.parse("https://drive.google.com/a/poli.ufrj.br/file/d/0B0Zmx65mhxBzNWxaTnN6aHZTNEk/view?usp=sharing");
                                        launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                                        startActivity(launchBrowser);
                                        break;
                                    case Calendar.THURSDAY:
                                        uriUrl = Uri.parse("https://drive.google.com/a/poli.ufrj.br/file/d/0B0Zmx65mhxBzb1owaVdiZnBRNzg/view?usp=sharing");
                                        launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                                        startActivity(launchBrowser);
                                        break;
                                    case Calendar.FRIDAY:
                                        uriUrl = Uri.parse("https://drive.google.com/a/poli.ufrj.br/file/d/0B0Zmx65mhxBzcVNwVVBrNEw2X2M/view?usp=sharing");
                                        launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                                        startActivity(launchBrowser);
                                        break;
                                }
                                break;

                            case 1:
                                switch (day){
                                    case Calendar.MONDAY:
                                        uriUrl = Uri.parse("https://drive.google.com/a/poli.ufrj.br/file/d/0B0Zmx65mhxBzMWp3YjNya3F2eDQ/view?usp=sharing");
                                        launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                                        startActivity(launchBrowser);
                                        break;
                                    case Calendar.TUESDAY:
                                        uriUrl = Uri.parse("https://drive.google.com/a/poli.ufrj.br/file/d/0B0Zmx65mhxBzOG5IOVI3YzVTYXM/view?usp=sharing");
                                        launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                                        startActivity(launchBrowser);
                                        break;
                                    case Calendar.WEDNESDAY:
                                        uriUrl = Uri.parse("https://drive.google.com/a/poli.ufrj.br/file/d/0B0Zmx65mhxBzNWxaTnN6aHZTNEk/view?usp=sharing");
                                        launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                                        startActivity(launchBrowser);
                                        break;
                                    case Calendar.THURSDAY:
                                        uriUrl = Uri.parse("https://drive.google.com/a/poli.ufrj.br/file/d/0B0Zmx65mhxBzb1owaVdiZnBRNzg/view?usp=sharing");
                                        launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                                        startActivity(launchBrowser);
                                        break;
                                    case Calendar.FRIDAY:
                                        uriUrl = Uri.parse("https://drive.google.com/a/poli.ufrj.br/file/d/0B0Zmx65mhxBzcVNwVVBrNEw2X2M/view?usp=sharing");
                                        launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                                        startActivity(launchBrowser);
                                        break;
                                }
                                break;

                            case 2:
                                switch (day){
                                    case Calendar.MONDAY:
                                        uriUrl = Uri.parse("https://drive.google.com/a/poli.ufrj.br/file/d/0B0Zmx65mhxBzMWp3YjNya3F2eDQ/view?usp=sharing");
                                        launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                                        startActivity(launchBrowser);
                                        break;
                                    case Calendar.TUESDAY:
                                        uriUrl = Uri.parse("https://drive.google.com/a/poli.ufrj.br/file/d/0B0Zmx65mhxBzOG5IOVI3YzVTYXM/view?usp=sharing");
                                        launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                                        startActivity(launchBrowser);
                                        break;
                                    case Calendar.WEDNESDAY:
                                        uriUrl = Uri.parse("https://drive.google.com/a/poli.ufrj.br/file/d/0B0Zmx65mhxBzNWxaTnN6aHZTNEk/view?usp=sharing");
                                        launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                                        startActivity(launchBrowser);
                                        break;
                                    case Calendar.THURSDAY:
                                        uriUrl = Uri.parse("https://drive.google.com/a/poli.ufrj.br/file/d/0B0Zmx65mhxBzb1owaVdiZnBRNzg/view?usp=sharing");
                                        launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                                        startActivity(launchBrowser);
                                        break;
                                    case Calendar.FRIDAY:
                                        uriUrl = Uri.parse("https://drive.google.com/a/poli.ufrj.br/file/d/0B0Zmx65mhxBzcVNwVVBrNEw2X2M/view?usp=sharing");
                                        launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                                        startActivity(launchBrowser);
                                        break;
                                }
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
        restCCMN.add("Desculpe, cardápio não disponível no momento.");

        List<String> restEBA = new ArrayList<String>();
        restEBA.add("Desculpe, cardápio não disponível no momento.");


        listDataChild.put(listDataHeader.get(0), restPoli);
        listDataChild.put(listDataHeader.get(1), restCCMN);
        listDataChild.put(listDataHeader.get(2), restEBA); // Header, Child data

    }
}
