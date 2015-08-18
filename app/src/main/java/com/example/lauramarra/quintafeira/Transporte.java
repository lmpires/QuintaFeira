package com.example.lauramarra.quintafeira;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;


public class Transporte extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transporte);

        final Uri uriUrl;

        Button tempoButton = (Button) findViewById(R.id.bTempo);

        uriUrl = Uri.parse("https://docs.google.com/a/poli.ufrj.br/spreadsheets/d/1BETsiRGmoQRDOXTr5HVZ6S8-pKm9nwwTsbvUOlR5Cuw/pubhtml?gid=1340033138&single=true");

        tempoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
            }
        });
    }
}
