package com.example.deean.medix;

/**
 * Created by Deean on 10.2.2016..
 */
public class Doktor {
    String id_doktor,ime, prezime, adresa, lozinka, telefon, oib,email,radno_vrijeme,rad_savjetovalista,mobitel,titula;


    public Doktor(String ime, String prezime, String adresa,String oib, String lozinka, String telefon,  String email, String radno_vrijeme, String rad_savjetovalista, String mobitel,String titula){

        this.ime=ime;
        this.prezime=prezime;
        this.adresa=adresa;
        this.lozinka=lozinka;
        this.telefon=telefon;
        this.oib=oib;
        this.email=email;
        this.radno_vrijeme=radno_vrijeme;
        this.rad_savjetovalista=rad_savjetovalista;
        this.mobitel=mobitel;
        this.titula=titula;
    }

    public Doktor(String email, String lozinka){
        this.email=email;
        this.lozinka=lozinka;
        this.ime="";
        this.prezime="";
        this.adresa="";
        this.telefon="";
        this.oib="";
        this.radno_vrijeme="";
        this.rad_savjetovalista="";
        this.mobitel="";
        this.titula="";
    }
    public Doktor(String email){
        this.email=email;
        this.lozinka="";
        this.ime="";
        this.prezime="";
        this.adresa="";
        this.telefon="";
        this.oib="";
        this.radno_vrijeme="";
        this.rad_savjetovalista="";
        this.mobitel="";
        this.titula="";
    }

    public Doktor(String id_doktor,String ime, String prezime, String adresa,String oib, String lozinka, String telefon,  String email, String radno_vrijeme, String rad_savjetovalista, String mobitel,String titula){
        this.id_doktor=id_doktor;
        this.ime=ime;
        this.prezime=prezime;
        this.adresa=adresa;
        this.lozinka=lozinka;
        this.telefon=telefon;
        this.oib=oib;
        this.email=email;
        this.radno_vrijeme=radno_vrijeme;
        this.rad_savjetovalista=rad_savjetovalista;
        this.mobitel=mobitel;
        this.titula=titula;
    }

}

