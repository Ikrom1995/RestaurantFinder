package com.example.mirza.restaurantfinder;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by mirza on 20.03.2017.
 */

public class GetWeather extends AsyncTask<String, Void, String> {

    private TextView textView;

    @Override
    protected String doInBackground(String... strings) {
        String weather = "Undefined";
        try{
            URL url=new URL(strings[0]);
            HttpURLConnection urlConnection=(HttpURLConnection) url.openConnection();
            urlConnection.connect();

            InputStream stream=new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(stream));

            StringBuilder builder=new StringBuilder();
            String inputString;
            while((inputString=bufferedReader.readLine())!=null){
                builder.append(inputString);
            }
            JSONObject topLevel=new JSONObject(builder.toString());
            JSONObject main=topLevel.getJSONObject("main");
            weather=String.valueOf(main.getDouble("temp"));

            urlConnection.disconnect();


        }catch (IOException | JSONException e){
            e.printStackTrace();
        }
        return weather;

    }

    public GetWeather(TextView textView) {
        this.textView = textView;
    }

    @Override
    protected void onPostExecute(String s) {
        textView.setText("Current weather is: " +s + " degrees" );
        super.onPostExecute(s);
    }

}
