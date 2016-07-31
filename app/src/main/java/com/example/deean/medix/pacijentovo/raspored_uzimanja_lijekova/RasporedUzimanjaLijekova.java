package com.example.deean.medix.pacijentovo.raspored_uzimanja_lijekova;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.deean.medix.R;
import com.example.deean.medix.databinding.ActivityRasporedUzimanjaLijekovaBinding;
import com.example.deean.medix.doktorovo.ToolbarActivity;

import com.example.deean.medix.pacijentovo.konstruktor_i_baza.Pacijent;
import com.example.deean.medix.pacijentovo.konstruktor_i_baza.PacijentLokalno;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RasporedUzimanjaLijekova extends ToolbarActivity {


    Pacijent pacijent;
    PacijentLokalno pacijentLokalno;
    ActivityRasporedUzimanjaLijekovaBinding bind;

    private ArrayList<AlarmKlasa> spremi;
    private List<AlarmKlasa> alarms;
    private RecyclerView rv;

    private String tekst;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this,R.layout.activity_raspored_uzimanja_lijekova);

        rv = (RecyclerView) findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        pacijentLokalno = new PacijentLokalno(this);
        pacijent = pacijentLokalno.getPrijavljenogPacijenta();
        postaviDrawer(postaviToolbar("Raspored uzimanja lijekova"), pacijent.getIme(), pacijent.getPrezime(), pacijent.getEmail()).build();

        bind.bPretraga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dohvatiAlarme();
            }
        });


        bind.ivDodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(),DodajNoviLijekZaUzimanje.class));
            }
        });

        dohvatiAlarme();
    }
    private void dohvatiAlarme(){
        bind.bPretraga.setVisibility(View.GONE);
        bind.loadingView.setVisibility(View.VISIBLE);

        tekst = String.valueOf(bind.etPretraga.getText());
        spremi = new ArrayList<>();
        alarms = new ArrayList<>();
        AlarmiAPI.Factory.getIstance().response(pacijent.getId_pacijent()).enqueue(new Callback<ArrayList<AlarmKlasa>>() {
            @Override
            public void onResponse(Call<ArrayList<AlarmKlasa>> call, Response<ArrayList<AlarmKlasa>> response) {
                bind.bPretraga.setVisibility(View.VISIBLE);
                bind.loadingView.setVisibility(View.GONE);
                initializeAdapter();
                for (int i = 0; i < response.body().size(); i++) {
                    spremi.add(new AlarmKlasa(response.body().get(i).getUnique_code(), response.body().get(i).getNaziv(), response.body().get(i).getVrijeme_pocetka_uzimanja(), response.body().get(i).getUzimati_svakih(),response.body().get(i).getSlika_lijeka()));
                }
                for (int i = 0; i < spremi.size(); i++) {
                    if (spremi.get(i).getNaziv().toLowerCase().contains(tekst.toLowerCase()) || spremi.get(i).getVrijeme_pocetka_uzimanja().toLowerCase().contains(tekst.toLowerCase())) {
                        alarms.add(new AlarmKlasa(spremi.get(i).getUnique_code(), spremi.get(i).getNaziv(), spremi.get(i).getVrijeme_pocetka_uzimanja(), spremi.get(i).getUzimati_svakih(), spremi.get(i).getSlika_lijeka()));
                    }


                }


            }
            @Override
            public void onFailure(Call<ArrayList<AlarmKlasa>> call, Throwable t) {
                bind.bPretraga.setVisibility(View.VISIBLE);
                bind.loadingView.setVisibility(View.GONE);
                Log.e("Failed", t.getMessage());
            }
        });
    }

    private void initializeAdapter(){
        RVAadapterAlarma adapter = new RVAadapterAlarma(RasporedUzimanjaLijekova.this,alarms);
        rv.setAdapter(adapter);
    }
}
