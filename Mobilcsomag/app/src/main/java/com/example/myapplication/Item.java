package com.example.myapplication;

public class Item {
    private String nev;
    private String leiras;
    private String ar;
    private int imgresource;

    public Item() {
    }

    public Item(String nev, String leiras, String ar, int imgresource) {
        this.nev = nev;
        this.leiras = leiras;
        this.ar = ar;
        this.imgresource = imgresource;
    }

    public String getLeiras() {
        return leiras;
    }

    public String getNev() {
        return nev;
    }

    public String getAr() {
        return ar;
    }

    public int getImgresource() {
        return imgresource;
    }
}
