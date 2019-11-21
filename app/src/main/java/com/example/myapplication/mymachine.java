package com.example.myapplication;

public class mymachine {

    String idlistmac,idmac,nameaddress;

    public mymachine() {
    }

    public mymachine(String idlistmac, String idmac, String nameaddress) {
        this.idlistmac = idlistmac;
        this.idmac = idmac;
        this.nameaddress = nameaddress;
    }

    public String getIdlistmac() {
        return idlistmac;
    }

    public void setIdlistmac(String idlistmac) {
        this.idlistmac = idlistmac;
    }

    public String getIdmac() {
        return idmac;
    }

    public void setIdmac(String idmac) {
        this.idmac = idmac;
    }

    public String getNameaddress() {
        return nameaddress;
    }

    public void setNameaddress(String nameaddress) {
        this.nameaddress = nameaddress;
    }
}
