package com.example.covid_19;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DailyUpdates extends AppCompatActivity {
    ListView daily;
    String respFromApi,damn,suy;
    TextView name,tcon,tdeaths,trecovered;
    //SearchView searchView;
    int x=0;
    ArrayList<DailyClass> days = new ArrayList<>();
    CountryArrayAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.countrydetails);
        Intent intent=getIntent();
        Log.d("reached","yep");
        name = findViewById(R.id.country);
        tcon = findViewById(R.id.textView6);
        tdeaths = findViewById(R.id.textView7);
        trecovered = findViewById(R.id.textView8);
        adapter = new CountryArrayAdapter(this,R.layout.adapter_daily_updates,days);
        daily = findViewById(R.id.dup);
        suy=intent.getStringExtra("countrySlug");
        //searchView=findViewById(R.id.sv1);
        Request request = new Request.Builder().url("https://api.covid19api.com/total/dayone/country/"+suy).get().build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                //Toast.makeText(DailyUpdates.this, "Request Failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful()) {
                    //Toast.makeText(DailyUpdates.this, "Request Failed", Toast.LENGTH_SHORT).show();
                } else {
                    respFromApi = response.body().string();
                    try {
                        Log.d("RawData", respFromApi);
                        extractData(respFromApi);
                    } catch (Exception e) {
                       // Toast.makeText(DailyUpdates.this, "Not Json", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
    void extractData(String respFromApi) throws Exception {
        JSONArray jsonArray = new JSONArray(respFromApi);
        for (int i=0;i<jsonArray.length();i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String day = "Day "+(i+1);
            String deaths = jsonObject.getString("Deaths");
            String active = jsonObject.getString("Active");
            String confirmed = jsonObject.getString("Confirmed");
            String d = jsonObject.getString("Date");
            String date="",time="";
            for (int z=0; z<d.length();z++){
                if (z<10){
                    date+=d.charAt(z);
                }
                else if(z>10 && z<19){
                    time+=d.charAt(z);
                }
            }
            String recovered = jsonObject.getString("Recovered");
            damn = jsonObject.getString("Country");
            DailyClass temp =  new DailyClass(day,date,confirmed,deaths,recovered,active,time);
            days.add(temp);
            x++;
        }
        Collections.reverse(days);
        DailyUpdates.Asynchronous asynk  = new Asynchronous();
        asynk.execute();
    }
    private class Asynchronous extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }

        @Override
        protected void onPostExecute(String s){
            daily.setAdapter(adapter);
            name.setText(damn);
            tcon.setText(days.get(0).confirmed);
            tdeaths.setText(days.get(0).deaths);
            trecovered.setText(days.get(0).recovered);
            /*searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    DailyUpdates.this.adapter.getFilter().filter(s);
                    return false;
                }
            });*/


        }
    }

}
