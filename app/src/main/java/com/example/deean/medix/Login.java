package com.example.deean.medix;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity  implements View.OnClickListener{
    Button bPrijava;
    EditText etEmail, etLozinka;
    TextView tvRegistracijaLink;

    DoktorLokalno DoktorLokalno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etLozinka = (EditText)findViewById(R.id.etLozinka);
        bPrijava = (Button) findViewById(R.id.bPrijava);
        tvRegistracijaLink = (TextView) findViewById(R.id.tvRegistracijaLink);


        bPrijava.setOnClickListener(this);
        tvRegistracijaLink.setOnClickListener(this);


        DoktorLokalno = new DoktorLokalno(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bPrijava:
                Doktor doktor = new Doktor(null,null,null,null,null,null,null);
                DoktorLokalno.spremiDoktorPodatke(doktor);
                DoktorLokalno.postaviPrijavljenogDoktora(true);


                break;
            case R.id.tvRegistracijaLink:
                startActivity(new Intent(this,Register.class));
                break;

        }

    }
}
