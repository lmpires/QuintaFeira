package com.example.lauramarra.quintafeira;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


public class NovoMenu extends ActionBarActivity {

    private Button btnSignOut, btnRevokeAccess;
    private ImageView imgProfilePic;
    private TextView txtName, txtEmail;
    private LinearLayout llProfileLayout;

    private ListView menuList;

    String[] menuOptions = new String[] { "Disciplinas","Alimentação", "Transporte", "Biblioteca", "Suporte ao Aluno"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the message from the intent
        Intent intent = getIntent();

        if(intent.getAction().equalsIgnoreCase("yes")){

        };

        // Create the text view
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText("NovoMenu");

        // Set the text view as the activity layout
        setContentView(textView);
    }
}
