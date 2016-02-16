package com.example.deean.medix;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



public class ZaboravljenaLozinka extends AppCompatActivity implements View.OnClickListener {
    Button bPosalji;
    EditText lozEmail;

    DoktorLokalno DoktorLokalno;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_zaboravljena_lozinka);
        lozEmail = (EditText) findViewById(R.id.lozEmail);
        bPosalji = (Button) findViewById(R.id.bPosalji);
        bPosalji.setOnClickListener(this);
        DoktorLokalno = new DoktorLokalno(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bPosalji:
                String email = lozEmail.getText().toString();
                Doktor doktor = new Doktor(email);


                autentifikacija(doktor);

                DoktorLokalno.spremiDoktorPodatke(doktor);

                break;
        }

    }


    private void autentifikacija(Doktor doktor){
        ServerRequest serverRequest = new ServerRequest(this);
        serverRequest.dohvatiEmailUpozadini(doktor, new GetUserCallback() {
            @Override
            public void done(Doktor returnedDoktor) {
                if(returnedDoktor == null){
                    showErrorMessage();
                }else{
                    prikaziPoruku();
                }
            }
        });

    }

    private void showErrorMessage(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ZaboravljenaLozinka.this);
        dialogBuilder.setMessage("Račun s takvim E-mailom ne postoji!");
        dialogBuilder.setPositiveButton("Ok",null);
        dialogBuilder.show();
    }

    private void prikaziPoruku(){
        AlertDialog.Builder dialogBuilder1 = new AlertDialog.Builder(ZaboravljenaLozinka.this);
        dialogBuilder1.setMessage("E-mail s upustvima je poslan!");
        dialogBuilder1.setPositiveButton("Ok", null);
        dialogBuilder1.show();
    }
}
