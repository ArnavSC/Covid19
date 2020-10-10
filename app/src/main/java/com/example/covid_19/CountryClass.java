package com.example.covid_19;

public class CountryClass {
    String name;
    String slug;
    String cnfmd;
    String deaths;
    String rcvd;
    String ncnfmd;
    String ndeaths;
    String nrcvd;
    String date;
    CountryClass(String name,String slug,String cnfmd, String deaths,String rcvd, String ncnfmd, String ndeaths, String nrcvd, String date)
    {
        this.name = name;
        this.slug = slug;
        this.cnfmd = cnfmd;
        this.deaths = deaths;
        this.rcvd = rcvd;
        this.ncnfmd = ncnfmd;
        this.ndeaths = ndeaths;
        this.nrcvd = nrcvd;
        this.date = date;
    }

}
