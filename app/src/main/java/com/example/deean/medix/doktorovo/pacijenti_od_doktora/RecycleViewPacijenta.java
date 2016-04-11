package com.example.deean.medix.doktorovo.pacijenti_od_doktora;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.deean.medix.doktorovo.konsturktor_i_baza.Doktor;
import com.example.deean.medix.doktorovo.konsturktor_i_baza.DoktorLokalno;
import com.example.deean.medix.pacijentovo.konstruktor_i_baza.Pacijent;
import com.example.deean.medix.R;
import com.example.deean.medix.doktorovo.pacijenti_u_bazi.RecycleViewPacijenata;
import com.example.deean.medix.doktorovo.ToolbarActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecycleViewPacijenta extends ToolbarActivity implements View.OnClickListener {

    public String tekst;
    Button bPretraga;
    EditText etPretraga;

    ImageView ivDodaj;

    private ArrayList<Pacijent> spremi;
    private List<Pacijent> pacijents;
    private RecyclerView rv;

    private Doktor doktor;
    com.example.deean.medix.doktorovo.konsturktor_i_baza.DoktorLokalno DoktorLokalno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view_pacijenta);

        rv = (RecyclerView) findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        etPretraga = (EditText) findViewById(R.id.etPretraga);
        bPretraga = (Button) findViewById(R.id.bPretraga);
        ivDodaj = (ImageView) findViewById(R.id.ivDodaj);


        bPretraga.setOnClickListener(this);
        ivDodaj.setOnClickListener(this);

        DoktorLokalno = new DoktorLokalno(this);

        doktor = DoktorLokalno.getPrijavljenogDoktora();
        postaviDrawer(postaviToolbar("Vaši pacijenti"),doktor.getIme().toUpperCase(),doktor.getPrezime().toUpperCase(),doktor.getEmail()).build();
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bPretraga:
                initializeData();
                break;
            case R.id.ivDodaj:
                startActivity(new Intent(this, RecycleViewPacijenata.class));
                break;
        }
    }
    private void initializeData(){
        tekst = String.valueOf(etPretraga.getText());
        spremi = new ArrayList<>();
        pacijents = new ArrayList<>();
        PacijentAPI.Factory.getIstance().response(doktor.getId_doktor()).enqueue(new Callback<ArrayList<Pacijent>>() {
            @Override
            public void onResponse(Call<ArrayList<Pacijent>> call, Response<ArrayList<Pacijent>> response) {
                initializeAdapter();
                for (int i = 0; i < response.body().size(); i++) {
                    spremi.add(new Pacijent(response.body().get(i).getIme(), response.body().get(i).getPrezime(), response.body().get(i).getAdresa(), response.body().get(i).getOib()));
                }
                for (int i = 0; i < spremi.size(); i++) {
                    if (spremi.get(i).getIme().toLowerCase().contains(tekst.toLowerCase()) || spremi.get(i).getPrezime().toLowerCase().contains(tekst.toLowerCase()) || spremi.get(i).getOib().contains(tekst.toLowerCase())) {
                        pacijents.add(new Pacijent(spremi.get(i).getIme(), spremi.get(i).getPrezime(), spremi.get(i).getAdresa(), spremi.get(i).getOib()));
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Pacijent>> call, Throwable t) {
                Log.e("Failed", t.getMessage());
            }
        });
    }
    private void initializeAdapter(){
        RVAdapterPacijenta adapter = new RVAdapterPacijenta(RecycleViewPacijenta.this,pacijents);
        rv.setAdapter(adapter);
    }
}
