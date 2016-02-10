package com.example.deean.medix;

/**
 * Created by Deean on 10.2.2016..
 */
public class Doktor {
    String ime, prezime, adresa, lozinka, telefon, oib,email;

    public Doktor(String ime, String prezime, String adresa,String oib, String lozinka, String telefon,  String email){

        this.ime=ime;
        this.prezime=prezime;
        this.adresa=adresa;
        this.lozinka=lozinka;
        this.telefon=telefon;
        this.oib=oib;
        this.email=email;
    }

    public Doktor(String email, String lozinka){
        this.email=email;
        this.lozinka=lozinka;
        this.ime="";
        this.prezime="";
        this.adresa="";
        this.telefon="";
        this.oib="";
    }

}

