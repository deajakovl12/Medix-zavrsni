package com.example.deean.medix;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Prijava extends AppCompatActivity implements View.OnClickListener {
    Button bOdjava;
    EditText etEmail, etOIB, etTelefon, etAdresa,etIme,etPrezime;
    DoktorLokalno DoktorLokalno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prijava);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etOIB = (EditText) findViewById(R.id.etOIB);
        etTelefon = (EditText) findViewById(R.id.etTelefon);
        etAdresa = (EditText) findViewById(R.id.etAdresa);
        etIme = (EditText) findViewById(R.id.etIme);
        etPrezime = (EditText) findViewById(R.id.etPrezime);
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
    }

    private boolean provjera(){
        return DoktorLokalno.provjeriPrijavljenogDoktora();
    }
    private void prikaziDoktorovePodatke(){
        Doktor doktor = DoktorLokalno.getPrijavljenogDoktora();
        etEmail.setText(doktor.email);
        etIme.setText(doktor.ime);
        etPrezime.setText(doktor.prezime);
        etOIB.setText(doktor.oib);
        etAdresa.setText(doktor.adresa);
        etTelefon.setText(doktor.telefon);

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
