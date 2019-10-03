package com.example.jokeapp;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button getJoke = (Button) findViewById(R.id.getJokeBtn);
        final TextView jokeTextView  = (TextView)findViewById(R.id.jokeTextView);

        getJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String jokeText;
                Thread thread = new Thread(new Runnable() {

                   @Override
                    public void run() {
                        try  {
                            URL url = null;
                            try

                            {
                                url = new URL("https://api.jokes.one/jod");
                            } catch(
                            MalformedURLException e)

                            {
                                e.printStackTrace();
                            }

                            try

                            {

                                //make connection
                                HttpsURLConnection urlc = (HttpsURLConnection) url.openConnection();

                                //get result
                                BufferedReader br = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
                                JSONObject jsonObject = new JSONObject(br.readLine());
                                br.close();

                                JSONObject jokesJSON    = new JSONObject((jsonObject.get("contents")).toString());
                                JSONArray arr = jokesJSON.getJSONArray("jokes");
                                JSONObject jokeJSON     = new JSONObject(arr.getJSONObject(0).get("joke").toString());
                                String jokeText  = jokeJSON.get("text").toString();
                                System.out.println(jokeText);
                                jokeTextView.setText(jokeText);


                            } catch(
                            Exception e)

                            {
                                System.out.println("Error occured");
                                System.out.println(e.toString());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();



               // Toast.makeText(getApplicationContext(),"Joke of the Day",Toast.LENGTH_LONG).show();
            }
        });

        Button jokeBtn = (Button) findViewById(R.id.googleBtn);
        jokeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String google = "http://www.yahoo.com";
                Uri webaddress = Uri.parse(google);

                Intent goToGoogle = new Intent(Intent.ACTION_VIEW, webaddress);
                if (goToGoogle.resolveActivity(getPackageManager())!= null){
                    startActivity(goToGoogle);

                }
            }
        });

    }
}
