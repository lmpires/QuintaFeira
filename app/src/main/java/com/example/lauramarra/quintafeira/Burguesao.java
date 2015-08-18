package com.example.lauramarra.quintafeira;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class Burguesao extends ActionBarActivity implements View.OnClickListener{

    public static final MediaType FORM_DATA_TYPE
            = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
    //URL derived from form URL
    public static final String URL="https://docs.google.com/forms/d/12k3RZCchO5SfgCq_cJv_aXgSCtYe4-pLr8rxXLBt2i0/formResponse";
    //input element ids found from the live form page
    public static final String HOURS_KEY="entry.259423524_hour";
    public static final String MINUTES_KEY="entry.259423524_minute";
    public static final String SECONDS_KEY="entry.259423524_second";

    private Context context;
    private Chronometer mChronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_burguesao);

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

        //save the activity in a context variable to be used afterwards
        context =this;

        mChronometer = (Chronometer)findViewById(R.id.chronometer);

        // Watch for button clicks.
        final Button bStart = (Button) findViewById(R.id.startButton);
        final Button bStop = (Button) findViewById(R.id.endButton);
        Button bReset = (Button)findViewById(R.id.resetButton);


        bStart.setOnClickListener(this);
        bReset.setOnClickListener(this);
        bStop.setOnClickListener(this);


    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.startButton:
                mChronometer.setBase(SystemClock.elapsedRealtime());
                mChronometer.start();
                break;
            case R.id.endButton:
                mChronometer.stop();

                String input = mChronometer.getText().toString();
                String[] split = input.split(":");

                //Create an object for PostDataTask AsyncTask
                PostDataTask postDataTask = new PostDataTask();

                //execute asynctask
                postDataTask.execute(URL,"0",
                        split[0],
                        split[1]);
                break;
            case R.id.resetButton:
                mChronometer.stop();
                mChronometer.setBase(SystemClock.elapsedRealtime());
                break;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //AsyncTask to send data as a http POST request
    private class PostDataTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... contactData) {
            Boolean result = true;
            String url = contactData[0];
            String hora = contactData[1];
            String minuto = contactData[2];
            String segundo = contactData[3];
            String postBody="";

            try {
                //all values must be URL encoded to make sure that special characters like & | ",etc.
                //do not cause problems
                postBody = HOURS_KEY+"=" + URLEncoder.encode(hora,"UTF-8")+
                        "&" + MINUTES_KEY + "=" + URLEncoder.encode(minuto,"UTF-8") +
                        "&" + SECONDS_KEY + "=" + URLEncoder.encode(segundo,"UTF-8");
            } catch (UnsupportedEncodingException ex) {
                result=false;
            }

            /*
            //If you want to use HttpRequest class from http://stackoverflow.com/a/2253280/1261816
            try {
			HttpRequest httpRequest = new HttpRequest();
			httpRequest.sendPost(url, postBody);
		}catch (Exception exception){
			result = false;
		}
            */

            try{
                //Create OkHttpClient for sending request
                OkHttpClient client = new OkHttpClient();
                //Create the request body with the help of Media Type
                RequestBody body = RequestBody.create(FORM_DATA_TYPE, postBody);
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
                //Send the request
                Response response = client.newCall(request).execute();
            }catch (IOException exception){
                result=false;
            }
            return result;
        }


        @Override
        protected void onPostExecute(Boolean result){
            //Print Success or failure message accordingly
            Toast.makeText(context,result?"Message successfully sent!":"There was some error in sending message. Please try again after some time.",Toast.LENGTH_LONG).show();
        }

    }
}
