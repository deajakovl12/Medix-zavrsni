package com.example.deean.medix;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Prijava extends AppCompatActivity implements View.OnClickListener {
    Button bOdjava;
    TextView etRadnoVrijeme, etSavjet, etTelefon, etAdresa,etPrezime,etIme,etMobitel,etTitula;
    DoktorLokalno DoktorLokalno;

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
        bOdjava = (Button) findViewById(R.id.bOdjava);

        bOdjava.setOnClickListener(this);

        DoktorLokalno = new DoktorLokalno(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
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
        Doktor doktor = DoktorLokalno.getPrijavljenogDoktora();
        etRadnoVrijeme.setText(doktor.radno_vrijeme);
        etIme.setText(doktor.ime.toUpperCase());
        etPrezime.setText(doktor.prezime.toUpperCase());
        etSavjet.setText(doktor.rad_savjetovalista);
        etAdresa.setText(doktor.adresa);
        etTelefon.setText(doktor.telefon);
        etMobitel.setText(doktor.mobitel);
        etTitula.setText(doktor.titula);

    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bOdjava:
                DoktorLokalno.obrisiDoktorPodatke();
                DoktorLokalno.postaviPrijavljenogDoktora(false);

                startActivity(new Intent(this, Login.class));

                break;

        }
    }
}
