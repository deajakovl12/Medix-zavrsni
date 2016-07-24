package com.example.deean.medix.pocetni_zaslon;

import android.app.AlertDialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deean.medix.doktorovo.konsturktor_i_baza.Doktor;
import com.example.deean.medix.doktorovo.konsturktor_i_baza.DoktorLokalno;
import com.example.deean.medix.registracija.FragmentRegistracija;
import com.example.deean.medix.doktorovo.GetUserCallback;
import com.example.deean.medix.pacijentovo.GetUserCallbackPacijent;
import com.example.deean.medix.pacijentovo.konstruktor_i_baza.Pacijent;
import com.example.deean.medix.pacijentovo.konstruktor_i_baza.PacijentLokalno;
import com.example.deean.medix.doktorovo.Prijava;
import com.example.deean.medix.pacijentovo.Prijava_Pacijent;
import com.example.deean.medix.R;
import com.example.deean.medix.ServerRequest;
import com.github.ybq.android.spinkit.SpinKitView;


public class Login extends ToolbarActivityAll implements View.OnClickListener{
    Button bPrijava;
    EditText etEmail, etLozinka;
    TextView tvRegistracijaLink, tvLozinkaLink;
    SpinKitView loadingView;

    Pacijent pacijent;

    com.example.deean.medix.doktorovo.konsturktor_i_baza.DoktorLokalno DoktorLokalno;
    com.example.deean.medix.pacijentovo.konstruktor_i_baza.PacijentLokalno PacijentLokalno;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etLozinka = (EditText)findViewById(R.id.etLozinka);
        bPrijava = (Button) findViewById(R.id.bPrijava);
        tvRegistracijaLink = (TextView) findViewById(R.id.tvRegistracijaLink);
        tvLozinkaLink = (TextView) findViewById(R.id.tvLozinkaLink);
        loadingView = (SpinKitView) findViewById(R.id.loading_view);


        bPrijava.setOnClickListener(this);
        tvRegistracijaLink.setOnClickListener(this);
        tvLozinkaLink.setOnClickListener(this);


        DoktorLokalno = new DoktorLokalno(this);
        PacijentLokalno = new PacijentLokalno(this);

        postaviDrawer(postaviToolbar("Početna")).build();


        if(DoktorLokalno.provjeriPrijavljenogDoktora()){
            startActivity(new Intent(this,Prijava.class));
        }
        if(PacijentLokalno.provjeriPrijavljenogPacijenta()){
            startActivity(new Intent(this,Prijava_Pacijent.class));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bPrijava:
                String email = etEmail.getText().toString();
                String lozinka = etLozinka.getText().toString();

                pacijent = new Pacijent(email,lozinka);
                Doktor doktor = new Doktor(email,lozinka);

                autentifikacija_doktora(doktor);

                //DoktorLokalno.spremiDoktorPodatke(doktor);
                //DoktorLokalno.postaviPrijavljenogDoktora(true);

                break;
            case R.id.tvRegistracijaLink:
                startActivity(new Intent(this,FragmentRegistracija.class));
                break;

            case R.id.tvLozinkaLink:
                startActivity(new Intent(this,ZaboravljenaLozinka.class));
                break;

        }



    }

    private void autentifikacija_doktora(Doktor doktor){
        ServerRequest serverRequest = new ServerRequest(this);
        bPrijava.setVisibility(View.GONE);
        loadingView.setVisibility(View.VISIBLE);
        serverRequest.dohvatiPodatkeUPozadini(doktor, new GetUserCallback() {
            @Override
            public void done(Doktor returnedDoktor) {
                if(returnedDoktor == null){
                    autentifikacija_pacijenta(pacijent);
                }else{
                    logDoktorIn(returnedDoktor);
                }
            }
        });

    }
    private void autentifikacija_pacijenta(Pacijent pacijent) {
        ServerRequest serverRequest = new ServerRequest(this);
        serverRequest.dohvatiPodatkePacijentUPozadini(pacijent, new GetUserCallbackPacijent() {
            @Override
            public void done(Pacijent returnedPacijent) {
                if (returnedPacijent == null) {
                    bPrijava.setVisibility(View.VISIBLE);
                    loadingView.setVisibility(View.GONE);
                    showErrorMessage();
                } else {
                    logPacijentIn(returnedPacijent);
                }
            }
        });
    }
    private void showErrorMessage(){
        //AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Login.this);
        //dialogBuilder.setMessage("Krivo uneseni podaci!");
        //dialogBuilder.setPositiveButton("Ok",null);
        Toast.makeText(Login.this, "Pogrešan email ili lozinka!", Toast.LENGTH_SHORT).show();
        //dialogBuilder.show();
    }

    private void logDoktorIn(Doktor returnedDoktor){
        DoktorLokalno.spremiDoktorPodatke(returnedDoktor);
        DoktorLokalno.postaviPrijavljenogDoktora(true);

        startActivity(new Intent(this,Prijava.class));
    }
    private void logPacijentIn(Pacijent returnedPacijent){
        PacijentLokalno.spremiPacijentPodatke(returnedPacijent);
        PacijentLokalno.postaviPrijavljenogPacijenta(true);

        startActivity(new Intent(this,Prijava_Pacijent.class));
    }
}
