package com.example.deean.medix;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Deean on 10.2.2016..
 */
public class DoktorLokalno {

    public static final String SP_NAME = "doktorDetalji";
    SharedPreferences doktorLokalnaBaza;

    public DoktorLokalno(Context context){
        doktorLokalnaBaza = context.getSharedPreferences(SP_NAME,0);

    }
    public void spremiDoktorPodatke(Doktor doktor){
        SharedPreferences.Editor spEditor = doktorLokalnaBaza.edit();
        spEditor.putString("ime",doktor.ime);
        spEditor.putString("prezime",doktor.prezime);
        spEditor.putString("OIB",doktor.oib);
        spEditor.putString("adresa ordinacije",doktor.adresa);
        spEditor.putString("telefon",doktor.telefon);
        spEditor.putString("lozinka",doktor.lozinka);
        spEditor.putString("email",doktor.email);
        spEditor.commit();
    }
    public Doktor getPrijavljenogDoktora(){
        String ime = doktorLokalnaBaza.getString("ime", "");
        String prezime = doktorLokalnaBaza.getString("prezime","");
        String oib = doktorLokalnaBaza.getString("OIB","");
        String adresa = doktorLokalnaBaza.getString("adresa","");
        String telefon = doktorLokalnaBaza.getString("telefon","");
        String lozinka = doktorLokalnaBaza.getString("lozinka","");
        String email = doktorLokalnaBaza.getString("email","");

        Doktor spremljeniDoktor = new Doktor(ime,prezime,adresa,lozinka,telefon,oib,email);
        return spremljeniDoktor;
    }
    public void postaviPrijavljenogDoktora(boolean prijavljen){
        SharedPreferences.Editor spEditor = doktorLokalnaBaza.edit();
        spEditor.putBoolean("prijavljen",prijavljen);
        spEditor.commit();
    }
    public boolean provjeriPrijavljenogDoktora(){
        if(doktorLokalnaBaza.getBoolean("prijavljen",false) == true){
                return true;
        }
        else{
            return false;

        }
        }
    public void obrisiDoktorPodatke(){
        SharedPreferences.Editor spEditor = doktorLokalnaBaza.edit();
        spEditor.clear();
        spEditor.commit();

    }
}
