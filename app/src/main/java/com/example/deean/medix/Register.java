package com.example.deean.medix;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Register extends AppCompatActivity implements View.OnClickListener {
    Button bRegistracija,bpacijent;
    EditText etEmail, etLozinka, etOIB, etTelefon, etAdresa, etIme, etPrezime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etLozinka = (EditText) findViewById(R.id.etLozinka);
        etOIB = (EditText) findViewById(R.id.etOIB);
        etTelefon = (EditText) findViewById(R.id.etTelefon);
        etAdresa = (EditText) findViewById(R.id.etAdresa);
        etIme = (EditText) findViewById(R.id.etIme);
        etPrezime = (EditText) findViewById(R.id.etPrezime);
        bRegistracija = (Button) findViewById(R.id.bRegistracija);
        bpacijent = (Button) findViewById(R.id.bpacijent);

        bRegistracija.setOnClickListener(this);
        bpacijent.setOnClickListener(this);
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

                    final Doktor doktor = new Doktor(ime, prezime, adresa, oib, lozinka, telefon, email, null, null, null, null);
                    Doktor doktor1 = new Doktor (email);
                    final Pacijent pacijent1 = new Pacijent(email);

                    final ServerRequest serverRequest = new ServerRequest(this);
                    serverRequest.dohvatiEmailUpozadini(doktor1, new GetUserCallback() {
                        @Override
                        public void done(Doktor returnedDoktor) {
                            if (returnedDoktor == null) {
                                serverRequest.dohvatiEmailUpozadiniPacijent(pacijent1, new GetUserCallbackPacijent() {
                                    @Override
                                    public void done(Pacijent returnedPacijent) {
                                        if (returnedPacijent == null) {
                                            registrirajDoktor(doktor);
                                        } else {
                                            showErrorMessage();}
                                    }});
                            } else {
                                showErrorMessage();
                            }
                        }
                    });
                } else{
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Register.this);
                    dialogBuilder.setMessage("Nije ispravan E-Mail!!");
                    dialogBuilder.show();
                }
                break;
            case R.id.bpacijent:
                startActivity(new Intent(Register.this,Register_Pacijent.class));

        }
    }
    private void registrirajDoktor(Doktor doktor){
        ServerRequest serverRequest = new ServerRequest(this);
        serverRequest.spremiPodatkeUPozadini(doktor, new GetUserCallback() {
            @Override
            public void done(Doktor returnedDoktor) {
                potvrdnaPoruka();
                startActivity(new Intent(Register.this,Login.class));
            }
        });
    }
    private void showErrorMessage(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Register.this);
        dialogBuilder.setMessage("Račun s takvim E-mailom je već kreiran!!");
        dialogBuilder.setPositiveButton("Ok", null);
        dialogBuilder.show();

    }
    private void potvrdnaPoruka(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Register.this);
        dialogBuilder.setMessage("Račun kreiran!");
        dialogBuilder.show();
    }

}
