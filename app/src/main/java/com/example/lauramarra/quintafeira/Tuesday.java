package com.example.lauramarra.quintafeira;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;
import android.widget.TextView;


public class Tuesday extends ActionBarActivity {

    MyDBHandler2 dbChecker;
    TextView textView;
    Button getStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuesday);

        textView = (TextView) findViewById(R.id.statusText);
        getStatus = (Button) findViewById(R.id.getStatus);


        dbChecker = new MyDBHandler2(this, null, null, 1);

        getID();

    }
    public void getID() {
        String dbString = dbChecker.getID();
        textView.setText(dbString);
    }
}
