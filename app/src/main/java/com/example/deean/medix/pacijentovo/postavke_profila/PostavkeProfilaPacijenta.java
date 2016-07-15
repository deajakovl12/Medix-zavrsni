package com.example.deean.medix.pacijentovo.postavke_profila;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.deean.medix.R;
import com.example.deean.medix.doktorovo.ToolbarActivity;
import com.example.deean.medix.pacijentovo.konstruktor_i_baza.Pacijent;
import com.example.deean.medix.pacijentovo.konstruktor_i_baza.PacijentLokalno;

public class PostavkeProfilaPacijenta extends ToolbarActivity {

    private Pacijent pacijent;
    PacijentLokalno pacijentLokalno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postavke_profila_pacijenta);
        pacijentLokalno = new PacijentLokalno(this);
        pacijent = pacijentLokalno.getPrijavljenogPacijenta();

        postaviDrawer(postaviToolbar("Postavke profila"), pacijent.getIme(), pacijent.getPrezime(), pacijent.getEmail()).build();

    }
}
