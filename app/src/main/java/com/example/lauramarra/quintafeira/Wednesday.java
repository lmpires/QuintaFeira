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

import java.util.Calendar;
import java.util.Locale;


public class Wednesday extends ActionBarActivity {

    TextView dayText;
    MyDBHandler dbHandler;
    MyDBHandler2 dbChecker;

    private ListView gradeDoDia;
    String[] menuOptions = new String[20];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wednesday);

        dayText = (TextView) findViewById(R.id.dayText);
        dayText.setText("Olá! " + "\n" + "Estas são suas aulas do dia:");

        gradeDoDia = (ListView) findViewById(R.id.gradeDoDia);

        dbChecker = new MyDBHandler2(this, null, null, 1);

        menuOptions = dbChecker.getID().split(";");

        ArrayAdapter<String> newAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuOptions);

        gradeDoDia.setAdapter(newAdapter);

        gradeDoDia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog alertDialog;

                dbHandler = new MyDBHandler(getApplicationContext(), null, null, 1);

                switch (position) {
                    case 0:

                        alertDialog = new AlertDialog.Builder(Wednesday.this).create();
                        alertDialog.setTitle(menuOptions[0]);
                        alertDialog.setMessage("Local: H217" + "\n" + "Status: Confimada!");
                        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                        alertDialog.show();
                        break;
                    case 1:
                        alertDialog = new AlertDialog.Builder(Wednesday.this).create();
                        alertDialog.setTitle(menuOptions[1]);
                        alertDialog.setMessage("Local: F112" + "\n" + "Status: Cancelada!");
                        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        alertDialog.show();
                        break;
                    case 2:
                        alertDialog = new AlertDialog.Builder(Wednesday.this).create();
                        alertDialog.setTitle(menuOptions[2]);
                        alertDialog.setMessage("Local: H217" + "\n" + "Status: Confimada!");
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


    public String[] getTodaysClass(){

        //Get day
        Calendar sCalendar = Calendar.getInstance();
        String day = sCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());

        dbChecker = new MyDBHandler2(this, null, null, 1);
        dbHandler = new MyDBHandler(this, null, null, 1);

        //Get codes
        String allCodes = dbChecker.getID();
        String[] codeArray = allCodes.split(";");

        //dayText.setText(codeArray[0]);

        String teste = dbHandler.getTodaysClass(codeArray[0], day);

        final String[] d = new String[] {dbHandler.getTodaysClass(codeArray[0], day),
                dbHandler.getTodaysClass(codeArray[1], day)};

        return d;
    }

}
