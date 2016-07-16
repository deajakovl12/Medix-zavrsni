package com.example.deean.medix.pacijentovo.raspored_uzimanja_lijekova;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.example.deean.medix.R;
import com.example.deean.medix.databinding.ActivityRasporedUzimanjaLijekovaBinding;
import com.example.deean.medix.doktorovo.ToolbarActivity;
import com.example.deean.medix.pacijentovo.konstruktor_i_baza.Pacijent;
import com.example.deean.medix.pacijentovo.konstruktor_i_baza.PacijentLokalno;

public class RasporedUzimanjaLijekova extends ToolbarActivity {


    Pacijent pacijent;
    PacijentLokalno pacijentLokalno;
    ActivityRasporedUzimanjaLijekovaBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this,R.layout.activity_raspored_uzimanja_lijekova);
        pacijentLokalno = new PacijentLokalno(this);
        pacijent = pacijentLokalno.getPrijavljenogPacijenta();
        postaviDrawer(postaviToolbar("Raspored uzimanja lijekova"), pacijent.getIme(), pacijent.getPrezime(), pacijent.getEmail()).build();

    }
}
