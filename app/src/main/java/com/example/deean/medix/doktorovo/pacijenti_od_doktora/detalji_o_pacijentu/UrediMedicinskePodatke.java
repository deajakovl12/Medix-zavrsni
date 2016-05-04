package com.example.deean.medix.doktorovo.pacijenti_od_doktora.detalji_o_pacijentu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.deean.medix.R;
import com.example.deean.medix.doktorovo.ToolbarActivity;
import com.example.deean.medix.doktorovo.konsturktor_i_baza.Doktor;
import com.example.deean.medix.doktorovo.konsturktor_i_baza.DoktorLokalno;

public class UrediMedicinskePodatke extends ToolbarActivity {
     private Doktor doktor;
     DoktorLokalno doktorLokalno;
     MedicinskiPodaciFragment mpf = new MedicinskiPodaciFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uredi_medicinske_podatke);

        doktorLokalno = new DoktorLokalno(this);


        doktor = doktorLokalno.getPrijavljenogDoktora();
        postaviDrawer(postaviToolbar("Uredi medicinske podatke"), doktor.getIme().toUpperCase(), doktor.getPrezime().toUpperCase(), doktor.getEmail()).build();

    }
}
