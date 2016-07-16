package com.example.deean.medix.doktorovo.pomoc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.deean.medix.R;
import com.example.deean.medix.doktorovo.ToolbarActivity;
import com.example.deean.medix.doktorovo.konsturktor_i_baza.Doktor;
import com.example.deean.medix.doktorovo.konsturktor_i_baza.DoktorLokalno;
import com.example.deean.medix.pacijentovo.konstruktor_i_baza.Pacijent;
import com.example.deean.medix.pacijentovo.konstruktor_i_baza.PacijentLokalno;

public class PomocDoktor extends ToolbarActivity {
    TextView tvPlus,tvPostavke,tvPregledi,tvLozinka,trecePitanje;
    Doktor doktor;
    DoktorLokalno doktorLokalno;

    Pacijent pacijent;
    PacijentLokalno pacijentLokalno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomoc_doktor);

        tvPlus = (TextView) findViewById(R.id.tvPlus);
        tvPostavke = (TextView) findViewById(R.id.tvPostavke);
        tvPregledi = (TextView) findViewById(R.id.tvPregled);
        tvLozinka = (TextView) findViewById(R.id.tvLozinka);
        trecePitanje = (TextView) findViewById(R.id.trecePitanje);

        doktorLokalno = new DoktorLokalno(this);
        doktor = doktorLokalno.getPrijavljenogDoktora();

        pacijentLokalno = new PacijentLokalno(this);
        pacijent = pacijentLokalno.getPrijavljenogPacijenta();

        if(doktorLokalno.provjeriPrijavljenogDoktora()) {
            postaviDrawer(postaviToolbar("Pomoć"), doktor.getIme().toUpperCase(), doktor.getPrezime().toUpperCase(), doktor.getEmail(), doktor.getSpol()).build();
            tvPlus.setText(R.string.plus_znak);
            tvPostavke.setText(R.string.postavke_profila);
            tvPregledi.setText(R.string.pregledi);
            tvLozinka.setText(R.string.lozinka);
        }
        else if(pacijentLokalno.provjeriPrijavljenogPacijenta()){
            tvPlus.setText(R.string.plus_znak_pacijent);
            tvPostavke.setText(R.string.postavke_profila);
            trecePitanje.setText("Kako si pacijent sam može namjestiti vrijeme uzimanja nekog lijeka?");
            tvPregledi.setText(R.string.uzimanje_lijekova);
            tvLozinka.setText(R.string.lozinka);
            postaviDrawer(postaviToolbar("Pomoć"),pacijent.getIme(),pacijent.getPrezime(),pacijent.getEmail()).build();
        }
    }
}
