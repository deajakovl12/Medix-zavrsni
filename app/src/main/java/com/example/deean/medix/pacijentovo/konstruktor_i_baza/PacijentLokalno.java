package com.example.deean.medix.pacijentovo.konstruktor_i_baza;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Deean on 10.2.2016..
 */
public class PacijentLokalno {

    public static final String SP_NAME = "pacijentDetalji";
    SharedPreferences pacijentLokalnaBaza;

    public PacijentLokalno(Context context){
        pacijentLokalnaBaza = context.getSharedPreferences(SP_NAME,0);
    }
    public void spremiPacijentPodatke(Pacijent pacijent){
        SharedPreferences.Editor spEditor = pacijentLokalnaBaza.edit();
        spEditor.putString("id_pacijent",pacijent.id_pacijent);
        spEditor.putString("ime",pacijent.ime);
        spEditor.putString("prezime",pacijent.prezime);
        spEditor.putString("adresa",pacijent.adresa);
        spEditor.putString("oib",pacijent.oib);
        spEditor.putString("lozinka",pacijent.lozinka);
        spEditor.putString("telefon",pacijent.telefon);
        spEditor.putString("email",pacijent.email);
        spEditor.putString("spol",pacijent.spol);
        spEditor.putString("mobitel",pacijent.mobitel);
        spEditor.putString("id_doktor",pacijent.id_doktor);
        spEditor.commit();
    }
    public Pacijent getPrijavljenogPacijenta(){
        String id_pacijent = pacijentLokalnaBaza.getString("id_pacijent","");
        String ime = pacijentLokalnaBaza.getString("ime", "");
        String prezime = pacijentLokalnaBaza.getString("prezime","");
        String adresa = pacijentLokalnaBaza.getString("adresa","");
        String oib = pacijentLokalnaBaza.getString("oib","");
        String lozinka = pacijentLokalnaBaza.getString("lozinka","");
        String telefon = pacijentLokalnaBaza.getString("telefon","");
        String email = pacijentLokalnaBaza.getString("email","");
        String spol = pacijentLokalnaBaza.getString("spol","");
        String mobitel = pacijentLokalnaBaza.getString("mobitel","");
        String id_doktor = pacijentLokalnaBaza.getString("id_doktor","");

        Pacijent spremljeniPacijent = new Pacijent(id_pacijent,ime,prezime,adresa,oib,lozinka,telefon,email,spol,mobitel,id_doktor);
        return spremljeniPacijent;
    }
    public void postaviPrijavljenogPacijenta(boolean prijavljen){
        SharedPreferences.Editor spEditor = pacijentLokalnaBaza.edit();
        spEditor.putBoolean("prijavljen",prijavljen);
        spEditor.commit();
    }
    public boolean provjeriPrijavljenogPacijenta(){
        if(pacijentLokalnaBaza.getBoolean("prijavljen",false) == true){
            return true;
        }
        else{
            return false;

        }
    }
    public void obrisiPacijentPodatke(){
        SharedPreferences.Editor spEditor = pacijentLokalnaBaza.edit();
        spEditor.clear();
        spEditor.commit();

    }
}
