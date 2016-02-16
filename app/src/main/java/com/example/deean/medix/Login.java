package com.example.deean.medix;

import android.app.AlertDialog;
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
    TextView tvRegistracijaLink, tvLozinkaLink;

    DoktorLokalno DoktorLokalno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etLozinka = (EditText)findViewById(R.id.etLozinka);
        bPrijava = (Button) findViewById(R.id.bPrijava);
        tvRegistracijaLink = (TextView) findViewById(R.id.tvRegistracijaLink);
        tvLozinkaLink = (TextView) findViewById(R.id.tvLozinkaLink);


        bPrijava.setOnClickListener(this);
        tvRegistracijaLink.setOnClickListener(this);
        tvLozinkaLink.setOnClickListener(this);


        DoktorLokalno = new DoktorLokalno(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bPrijava:
                String email = etEmail.getText().toString();
                String lozinka = etLozinka.getText().toString();



                Doktor doktor = new Doktor(email,lozinka);

                autentifikacija(doktor);

                DoktorLokalno.spremiDoktorPodatke(doktor);
                DoktorLokalno.postaviPrijavljenogDoktora(true);



                break;
            case R.id.tvRegistracijaLink:
                startActivity(new Intent(this,Register.class));
                break;

            case R.id.tvLozinkaLink:
                startActivity(new Intent(this,ZaboravljenaLozinka.class));
                break;

        }



    }

    private void autentifikacija(Doktor doktor){
        ServerRequest serverRequest = new ServerRequest(this);
        serverRequest.dohvatiPodatkeUPozadini(doktor, new GetUserCallback() {
            @Override
            public void done(Doktor returnedDoktor) {
                if(returnedDoktor == null){
                    showErrorMessage();
                }else{
                    logDoktorIn(returnedDoktor);
                }
            }
        });

    }
    private void showErrorMessage(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Login.this);
        dialogBuilder.setMessage("Krivo uneseni podaci!");
        dialogBuilder.setPositiveButton("Ok",null);
        dialogBuilder.show();
    }

    private void logDoktorIn(Doktor returnedDoktor){
        DoktorLokalno.spremiDoktorPodatke(returnedDoktor);
        DoktorLokalno.postaviPrijavljenogDoktora(true);

        startActivity(new Intent(this,Prijava.class));

    }
}
