package com.example.deean.medix;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Register extends AppCompatActivity implements View.OnClickListener {
    Button bRegistracija;
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

        bRegistracija.setOnClickListener(this);
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

                Doktor doktor = new Doktor(ime,prezime,adresa,oib,lozinka,telefon,email,null,null,null,null);

                registrirajDoktor(doktor);
                break;
        }
    }
    private void registrirajDoktor(Doktor doktor){
        ServerRequest serverRequest = new ServerRequest(this);
        serverRequest.spremiPodatkeUPozadini(doktor, new GetUserCallback() {
            @Override
            public void done(Doktor returnedDoktor) {
                startActivity(new Intent(Register.this,Login.class));
            }
        });
    }
}
