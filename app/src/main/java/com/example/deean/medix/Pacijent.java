package com.example.deean.medix;

/**
 * Created by Deean on 10.2.2016..
 */
public class Pacijent {
    String ime, prezime, adresa, lozinka, telefon, oib,email,spol,mobitel;

    public Pacijent(String ime, String prezime, String adresa,String oib, String lozinka, String telefon,  String email, String spol,String mobitel){

        this.ime=ime;
        this.prezime=prezime;
        this.adresa=adresa;
        this.lozinka=lozinka;
        this.telefon=telefon;
        this.oib=oib;
        this.email=email;
        this.spol=spol;
        this.mobitel=mobitel;
    }

    public Pacijent(String email, String lozinka){
        this.email=email;
        this.lozinka=lozinka;
        this.ime="";
        this.prezime="";
        this.adresa="";
        this.telefon="";
        this.oib="";
        this.spol="";
        this.mobitel="";
    }
    public Pacijent(String email){
        this.email=email;
        this.lozinka="";
        this.ime="";
        this.prezime="";
        this.adresa="";
        this.telefon="";
        this.oib="";
        this.spol="";
        this.mobitel="";
    }

}

