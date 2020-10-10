package com.example.covid_19;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    SearchView searchView;
    TextView globa,nconf,conf,deaths,ndeaths,recv,nrecv;
    String respFromApi,nc,tc,nr,tr,nd,td;
    ArrayAdapter arrayAdapter;
    ArrayList<String> countryNames=new ArrayList<String>();
    List<CountryClass> country =new ArrayList<CountryClass>();
    //String[] names = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.newList);
        searchView = findViewById(R.id.search);
        globa = findViewById(R.id.Global);
        conf = findViewById(R.id.conf);
        nconf = findViewById(R.id.newconf);
        deaths = findViewById(R.id.deaths);
        ndeaths = findViewById(R.id.ndeaths);
        recv = findViewById(R.id.recv);
        nrecv = findViewById(R.id.nrecv);
        arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,android.R.id.text1,countryNames);
        Request request = new Request.Builder().url("https://api.covid19api.com/summary").get().build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Toast.makeText(MainActivity.this, "Request Failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Request Failed", Toast.LENGTH_SHORT).show();
                } else {
                    respFromApi = response.body().string();
                    try {
                        Log.d("RawData", respFromApi);
                        extractData(respFromApi);
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Not Json", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        //nconf.setText(ncc);
    }
    void extractData(String respFromApi) throws Exception{
        JSONObject jsonObject = new JSONObject(respFromApi);
        JSONObject globaldat = jsonObject.getJSONObject("Global");
        nc = globaldat.getString("NewConfirmed");
        tc = globaldat.getString("TotalConfirmed");
        nd = globaldat.getString("NewDeaths");
        td = globaldat.getString("TotalDeaths");
        nr = globaldat.getString("NewRecovered");
        tr = globaldat.getString("TotalRecovered");
        JSONArray countrydat=jsonObject.getJSONArray("Countries");
        for (int i=0;i<countrydat.length();i++){
            JSONObject cd = countrydat.getJSONObject(i);
            String cnc = cd.getString("NewConfirmed");
            String ctc = cd.getString("TotalConfirmed");
            String cnd = cd.getString("NewDeaths");
            String ctd = cd.getString("TotalDeaths");
            String cnr = cd.getString("NewRecovered");
            String ctr = cd.getString("TotalRecovered");
            String cname = cd.getString("Country");//+"\nConfirmed: "+ctc;
            String cslug = cd.getString("Slug");
            String date = cd.getString("Date");
            CountryClass newcc = new CountryClass(cname,cslug,ctc,ctd,ctr,cnc,cnd,cnr,date);
            country.add(newcc);
            countryNames.add(cname);
            //conNam[i]=cname;
        }

        Asynchronous asynk  = new Asynchronous();
        asynk.execute();
    }
    private class Asynchronous extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            nconf.setText("Confirmed Today: "+nc);
            conf.setText("Confirmed: "+tc);
            deaths.setText("Deaths: "+td);
            ndeaths.setText("Deaths Today: "+nd);
            recv.setText("Recovered: "+tr);
            nrecv.setText("Recovered Today: "+nr);

            listView.setAdapter(arrayAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                {
                    Log.d("selected","drag");
                }
            });
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    MainActivity.this.arrayAdapter.getFilter().filter(s);
                    return false;
                }
            });
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String selectedCountry = (String) arrayAdapter.getItem(i);
                    Intent intent= new Intent(MainActivity.this,DailyUpdates.class);
                    int s = countryNames.indexOf(selectedCountry);
                    Log.d("Mailed",country.get(s).slug);
                    intent.putExtra("countrySlug",country.get(s).slug);
                    intent.putExtra("countryName",country.get(s).name);
                    startActivity(intent);
                }
            });
        }
    }
        /**/



}