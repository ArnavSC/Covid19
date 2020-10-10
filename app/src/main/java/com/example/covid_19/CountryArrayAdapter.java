package com.example.covid_19;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CountryArrayAdapter extends ArrayAdapter<DailyClass> {

    private Context mContext;
    int mResource;

    public CountryArrayAdapter(@NonNull Context context, int resource, @NonNull ArrayList<DailyClass> objects) {
        super(context, resource, objects);
        mContext=context;
        mResource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String day = getItem(position).getDay();
        String date = getItem(position).getDate();
        String confirmed = getItem(position).getConfirmed();
        String deaths = getItem(position).getDeaths();
        String recovered = getItem(position).getRecovered();
        String active = getItem(position).getActive();
        String time = getItem(position).getTime();

        DailyClass person = new DailyClass(day,date,confirmed,deaths,recovered,active,time);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);
        TextView tvday = (TextView) convertView.findViewById(R.id.textView);
        TextView tvdate = (TextView) convertView.findViewById(R.id.date);
        TextView tvconfirmed = (TextView) convertView.findViewById(R.id.confirmed);
        TextView tvdeaths = (TextView) convertView.findViewById(R.id.death);
        TextView tvrecovered = (TextView) convertView.findViewById(R.id.recovered);
        TextView tvactive = (TextView) convertView.findViewById(R.id.active);

        tvday.setText(day);
        tvdate.setText(date+"\t"+"Time: "+time);
        tvconfirmed.setText(confirmed);
        tvdeaths.setText(deaths);
        tvrecovered.setText(recovered);
        tvactive.setText(active);

        return convertView;
    }

}
