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
        spEditor.putString("adresa",doktor.adresa);
        spEditor.putString("oib",doktor.oib);
        spEditor.putString("lozinka",doktor.lozinka);
        spEditor.putString("telefon",doktor.telefon);
        spEditor.putString("email",doktor.email);
        spEditor.putString("radno_vrijeme",doktor.radno_vrijeme);
        spEditor.putString("rad_savjetovalista",doktor.rad_savjetovalista);
        spEditor.putString("mobitel",doktor.mobitel);
        spEditor.putString("titula",doktor.titula);
        spEditor.commit();
    }
    public Doktor getPrijavljenogDoktora(){
        String ime = doktorLokalnaBaza.getString("ime", "");
        String prezime = doktorLokalnaBaza.getString("prezime","");
        String adresa = doktorLokalnaBaza.getString("adresa","");
        String oib = doktorLokalnaBaza.getString("oib","");
        String lozinka = doktorLokalnaBaza.getString("lozinka","");
        String telefon = doktorLokalnaBaza.getString("telefon","");
        String email = doktorLokalnaBaza.getString("email","");
        String radno_vrijeme = doktorLokalnaBaza.getString("radno_vrijeme","");
        String rad_savjetovalista = doktorLokalnaBaza.getString("rad_savjetovalista","");
        String mobitel = doktorLokalnaBaza.getString("mobitel","");
        String titula = doktorLokalnaBaza.getString("titula","");

        Doktor spremljeniDoktor = new Doktor(ime,prezime,adresa,oib,lozinka,telefon,email,radno_vrijeme,rad_savjetovalista,mobitel,titula);
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
