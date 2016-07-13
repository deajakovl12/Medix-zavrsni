package com.example.deean.medix.doktorovo;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deean.medix.doktorovo.konsturktor_i_baza.Doktor;
import com.example.deean.medix.doktorovo.konsturktor_i_baza.DoktorLokalno;
import com.example.deean.medix.R;
import com.example.deean.medix.pocetni_zaslon.Login;
import com.squareup.picasso.Picasso;


public class Prijava extends ToolbarActivity {
    TextView etRadnoVrijeme, etSavjet, etTelefon, etAdresa,etPrezime,etIme,etMobitel,etTitula;
    ImageView ivDoktor;
    com.example.deean.medix.doktorovo.konsturktor_i_baza.DoktorLokalno DoktorLokalno;

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
        ivDoktor = (ImageView) findViewById(R.id.ivDoktor);

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
        etRadnoVrijeme.setText(doktor.getRadno_vrijeme());
        etIme.setText(doktor.getIme().toUpperCase());
        etPrezime.setText(doktor.getPrezime().toUpperCase());
        etSavjet.setText(doktor.getRad_savjetovalista());
        etAdresa.setText(doktor.getAdresa());
        etTelefon.setText(doktor.getTelefon());
        etMobitel.setText(doktor.getMobitel());
        etTitula.setText(doktor.getTitula());

        if(doktor.getSpol().equals("Musko")){
            Picasso.with(getApplicationContext()).load(R.drawable.doctor_red).into(ivDoktor);
        }
        else if(doktor.getSpol().equals("Zensko")){
            Picasso.with(getApplicationContext()).load(R.drawable.doktorica_red).into(ivDoktor);
        }

        postaviDrawer(postaviToolbar("Doktor"),etIme.getText().toString(),etPrezime.getText().toString(), doktor.getEmail(),doktor.getSpol()).build();
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
