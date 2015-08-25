package com.example.lauramarra.quintafeira;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class Terca extends ActionBarActivity {
    TextView dayText;
    MyDBHandler dbHandler;
    MyDBHandler2 dbChecker;
    private ListView gradeDoDia;
    String[] menuOptionsName, menuOptionsCode = new String[10];
    final String[] local = new String[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terca);

        dayText = (TextView) findViewById(R.id.dayText);
        dayText.setText("Olá! " + "\n" + "\n" + "Estas são suas aulas do dia:");

        gradeDoDia = (ListView) findViewById(R.id.gradeDoDia);

        dbChecker = new MyDBHandler2(this, null, null, 1);
        dbHandler = new MyDBHandler(getApplicationContext(), null, null, 1);

        menuOptionsName = dbChecker.getClassName("terca").split(";");
        menuOptionsCode = dbChecker.getClassID("terca").split(";");

        for(int i = 0; i < menuOptionsName.length; i++) {
            local[i]= dbHandler.getLocal(menuOptionsCode[i]);
        }
        ArrayAdapter<String> newAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuOptionsName);

        gradeDoDia.setAdapter(newAdapter);

        gradeDoDia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog alertDialog;
                String cut;

                switch (position) {
                    case 0:
                        cut = local[0].substring(0,4);
                        alertDialog = new AlertDialog.Builder(Terca.this).create();
                        alertDialog.setTitle(menuOptionsName[0]);
                        alertDialog.setMessage("Local: " + cut + "\n" + "Status: Confimada!");
                        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        alertDialog.show();
                        break;
                    case 1:
                        cut = local[1].substring(0, 4);
                        alertDialog = new AlertDialog.Builder(Terca.this).create();
                        alertDialog.setTitle(menuOptionsName[1]);
                        alertDialog.setMessage("Local: " + cut + "\n" + "Status: Cancelada!");
                        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        alertDialog.show();
                        break;
                    case 2:
                        cut = local[2].substring(0, 4);
                        alertDialog = new AlertDialog.Builder(Terca.this).create();
                        alertDialog.setTitle(menuOptionsName[2]);
                        alertDialog.setMessage("Local: " + cut + "\n" + "Status: Confimada!");
                        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        alertDialog.show();
                        break;
                }
            }

        });
    }
}
