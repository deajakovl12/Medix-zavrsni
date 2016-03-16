package com.example.deean.medix;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


public class Prijava extends ToolbarActivity {
    TextView etRadnoVrijeme, etSavjet, etTelefon, etAdresa,etPrezime,etIme,etMobitel,etTitula;
    DoktorLokalno DoktorLokalno;

    private Doktor doktor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prijava);


        etRadnoVrijeme = (TextView) findViewById(R.id.etRadnoVrijeme);
        etSavjet = (TextView) findViewById(R.id.etSavjet);
        etTelefon = (TextView) findViewById(R.id.etTelefon);
        etAdresa = (TextView) findViewById(R.id.etAdresa);
        etIme = (TextView) findViewById(R.id.etIme);
        etPrezime = (TextView) findViewById(R.id.etPrezime);
        etMobitel = (TextView) findViewById(R.id.etMobitel);
        etTitula = (TextView) findViewById(R.id.etTitula);

        DoktorLokalno = new DoktorLokalno(this);
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.e("Blabla", "pokrenuto");
        if(provjera()==true){
            prikaziDoktorovePodatke();
        }
        else{
            startActivity(new Intent(Prijava.this,Login.class));
        }
    }

    private boolean provjera(){
        return DoktorLokalno.provjeriPrijavljenogDoktora();
    }

    private void prikaziDoktorovePodatke(){

        doktor = DoktorLokalno.getPrijavljenogDoktora();
        etRadnoVrijeme.setText(doktor.radno_vrijeme);
        etIme.setText(doktor.ime.toUpperCase());
        etPrezime.setText(doktor.prezime.toUpperCase());
        etSavjet.setText(doktor.rad_savjetovalista);
        etAdresa.setText(doktor.adresa);
        etTelefon.setText(doktor.telefon);
        etMobitel.setText(doktor.mobitel);
        etTitula.setText(doktor.titula);

        postaviDrawer(postaviToolbar("Doktor"),etIme.getText().toString(),etPrezime.getText().toString(), doktor.email).build();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Blabla","unisteno");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("Blabla", "pauzirano");


    }
}
