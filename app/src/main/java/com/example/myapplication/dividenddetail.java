package com.example.myapplication;

public class dividenddetail {
    String idrow,date,money;

    public dividenddetail() {
    }

    public dividenddetail(String idrow, String date, String money) {
        this.idrow = idrow;
        this.date = date;
        this.money = money;
    }

    public String getIdrow() {
        return idrow;
    }

    public void setIdrow(String idrow) {
        this.idrow = idrow;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
