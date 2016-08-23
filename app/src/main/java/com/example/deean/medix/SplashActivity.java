package com.example.deean.medix;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.deean.medix.doktorovo.Prijava;
import com.example.deean.medix.doktorovo.konsturktor_i_baza.DoktorLokalno;
import com.example.deean.medix.pacijentovo.Prijava_Pacijent;
import com.example.deean.medix.pacijentovo.konstruktor_i_baza.PacijentLokalno;
import com.example.deean.medix.pocetni_zaslon.Login;

public class SplashActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 2000;


    DoktorLokalno DoktorLokalno;
    PacijentLokalno PacijentLokalno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        DoktorLokalno = new DoktorLokalno(this);
        PacijentLokalno = new PacijentLokalno(this);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if(DoktorLokalno.provjeriPrijavljenogDoktora()){
                    startActivity(new Intent(SplashActivity.this,Prijava.class));
                }
                else if(PacijentLokalno.provjeriPrijavljenogPacijenta()){
                    startActivity(new Intent(SplashActivity.this,Prijava_Pacijent.class));
                }
                else{
                    startActivity(new Intent(SplashActivity.this,Login.class));
                }
            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}
