package com.example.myapplication;

public class news {

    String idnews,imgpost,valuenews,date;

    public news() {
    }

    public news(String idnews, String imgpost, String valuenews, String date) {
        this.idnews = idnews;
        this.imgpost = imgpost;
        this.valuenews = valuenews;
        this.date = date;
    }

    public String getIdnews() {
        return idnews;
    }

    public void setIdnews(String idnews) {
        this.idnews = idnews;
    }

    public String getImgpost() {
        return imgpost;
    }

    public void setImgpost(String imgpost) {
        this.imgpost = imgpost;
    }

    public String getValuenews() {
        return valuenews;
    }

    public void setValuenews(String valuenews) {
        this.valuenews = valuenews;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
