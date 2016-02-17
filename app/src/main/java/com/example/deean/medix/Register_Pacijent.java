package com.example.deean.medix;

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

                Pacijent pacijent = new Pacijent(ime, prezime, adresa, oib, lozinka, telefon, email, null, null);

                registrirajPacijent(pacijent);
                break;
            case R.id.bdoktor:
                startActivity(new Intent(Register_Pacijent.this, Register.class));

        }
    }

    private void registrirajPacijent(Pacijent pacijent) {
        ServerRequest serverRequest = new ServerRequest(this);
        serverRequest.spremiPodatkePacijentUPozadini(pacijent, new GetUserCallbackPacijent() {
            @Override
            public void done(Pacijent returnedPacijent) {
                startActivity(new Intent(Register_Pacijent.this, Login.class));
            }
        });
    }
}
