package com.example.deean.medix;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Register_Pacijent extends AppCompatActivity implements View.OnClickListener {
    Button bRegistracija, bdoktor;
    EditText etEmail, etLozinka, etOIB, etTelefon, etAdresa, etIme, etPrezime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_pacijent);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etLozinka = (EditText) findViewById(R.id.etLozinka);
        etOIB = (EditText) findViewById(R.id.etOIB);
        etTelefon = (EditText) findViewById(R.id.etTelefon);
        etAdresa = (EditText) findViewById(R.id.etAdresa);
        etIme = (EditText) findViewById(R.id.etIme);
        etPrezime = (EditText) findViewById(R.id.etPrezime);
        bRegistracija = (Button) findViewById(R.id.bRegistracija);
        bdoktor = (Button) findViewById(R.id.bdoktor);

        bRegistracija.setOnClickListener(this);
        bdoktor.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bRegistracija:
                String ime = etIme.getText().toString();
                String prezime = etPrezime.getText().toString();
                String email = etEmail.getText().toString();
                String telefon = etTelefon.getText().toString();
                String adresa = etAdresa.getText().toString();
                String oib = etOIB.getText().toString();
                String lozinka = etLozinka.getText().toString();
                if(email.contains("@")) {

                    final Pacijent pacijent3 = new Pacijent(ime, prezime, adresa, oib, lozinka, telefon, email, null, null);
                    Doktor doktor2 = new Doktor (email);
                    final Pacijent pacijent2 = new Pacijent(email);

                    final ServerRequest serverRequest = new ServerRequest(this);
                    /*serverRequest.dohvatiEmailUpozadiniPacijent(pacijent2, new GetUserCallbackPacijent() {
                        @Override
                        public void done(Pacijent returnedPacijent) {
                            if (returnedPacijent == null) {
                                serverRequest.dohvatiEmailUpozadini(doktor2, new GetUserCallback() {
                                    @Override
                                    public void done(Doktor returnedDoktor) {
                                        if (returnedDoktor == null) {
                                            registrirajPacijent(pacijent3);
                                        } else {
                                            showErrorMessage();}
                                    }});
                            } else {
                                showErrorMessage();
                            }
                        }
                    });*/
                    serverRequest.dohvatiEmailUpozadini(doktor2, new GetUserCallback() {
                        @Override
                        public void done(Doktor returnedDoktor) {
                            if (returnedDoktor == null) {
                                serverRequest.dohvatiEmailUpozadiniPacijent(pacijent2, new GetUserCallbackPacijent() {
                                    @Override
                                    public void done(Pacijent returnedPacijent) {
                                        if (returnedPacijent == null) {
                                            registrirajPacijent(pacijent3);
                                        } else {
                                            showErrorMessage();
                                        }
                                    }
                                });
                            } else {
                                showErrorMessage();
                            }
                        }
                    });
                }
                else{
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Register_Pacijent.this);
                dialogBuilder.setMessage("Nije ispravan E-Mail!!");
                dialogBuilder.show();
            }
                break;
            case R.id.bdoktor:
                Intent to_doktor = new Intent(Register_Pacijent.this,Register.class);
                to_doktor.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(to_doktor);


        }
    }

    private void registrirajPacijent(Pacijent pacijent) {
        ServerRequest serverRequest = new ServerRequest(this);
        serverRequest.spremiPodatkePacijentUPozadini(pacijent, new GetUserCallbackPacijent() {
            @Override
            public void done(Pacijent returnedPacijent) {
                potvrdnaPoruka();
                startActivity(new Intent(Register_Pacijent.this, Login.class));
            }
        });
    }
    private void showErrorMessage(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Register_Pacijent.this);
        dialogBuilder.setMessage("Račun s takvim E-mailom je već kreiran!!");
        dialogBuilder.setPositiveButton("Ok", null);
        dialogBuilder.show();

    }
    private void potvrdnaPoruka(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Register_Pacijent.this);
        dialogBuilder.setMessage("Račun kreiran!");
        dialogBuilder.show();
    }
}
