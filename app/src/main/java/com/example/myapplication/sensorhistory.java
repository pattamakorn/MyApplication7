package com.example.myapplication;

public class sensorhistory {
    String date,idqueue,moist,ph,light;

    public sensorhistory() {
    }

    public sensorhistory(String date, String idqueue, String moist, String ph, String light) {
        this.date = date;
        this.idqueue = idqueue;
        this.moist = moist;
        this.ph = ph;
        this.light = light;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIdqueue() {
        return idqueue;
    }

    public void setIdqueue(String idqueue) {
        this.idqueue = idqueue;
    }

    public String getMoist() {
        return moist;
    }

    public void setMoist(String moist) {
        this.moist = moist;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public String getLight() {
        return light;
    }

    public void setLight(String light) {
        this.light = light;
    }
}
