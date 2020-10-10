package com.example.covid_19;

public class DailyClass {
    String day;
    String date;
    String confirmed;
    String deaths;
    String recovered;
    String active;
    String time;



    DailyClass(String day, String date, String confirmed, String deaths, String recovered, String active, String time)
    {
        this.day = day;
        this.confirmed = confirmed;
        this.deaths = deaths;
        this.recovered = recovered;
        this.date = date;
        this.active = active;
        this.time=time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
