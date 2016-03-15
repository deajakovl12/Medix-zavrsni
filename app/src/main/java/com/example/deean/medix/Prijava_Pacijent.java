package com.example.deean.medix;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Prijava_Pacijent extends ToolbarActivityPacijent {
    TextView  etSpol, etTelefon, etAdresa,etPrezime,etIme,etMobitel,etOIB;
    PacijentLokalno PacijentLokalno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prijava__pacijent);

        etSpol = (TextView) findViewById(R.id.etSpol);
        etTelefon = (TextView) findViewById(R.id.etTelefon);
        etAdresa = (TextView) findViewById(R.id.etAdresa);
        etIme = (TextView) findViewById(R.id.etIme);
        etPrezime = (TextView) findViewById(R.id.etPrezime);
        etMobitel = (TextView) findViewById(R.id.etMobitel);
        etOIB = (TextView) findViewById(R.id.etOIB);

        PacijentLokalno = new PacijentLokalno(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(provjera()==true){
            prikaziPacijentovePodatke();
        }
        else{
            startActivity(new Intent(Prijava_Pacijent.this,Login.class));
        }
    }

    private boolean provjera(){
        return PacijentLokalno.provjeriPrijavljenogPacijenta();
    }
    private void prikaziPacijentovePodatke(){
        Pacijent pacijent = PacijentLokalno.getPrijavljenogPacijenta();
        etSpol.setText(pacijent.spol);
        etIme.setText(pacijent.ime.toUpperCase());
        etPrezime.setText(pacijent.prezime.toUpperCase());
        etAdresa.setText(pacijent.adresa);
        etTelefon.setText(pacijent.telefon);
        etMobitel.setText(pacijent.mobitel);
        etOIB.setText(pacijent.oib);

        postaviDrawer(postaviToolbar("Pacijent"),etIme.getText().toString(),etPrezime.getText().toString(),pacijent.email).build();

    }
}
