package com.example.deean.medix.doktorovo.pacijenti_u_bazi;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.deean.medix.doktorovo.konsturktor_i_baza.Doktor;
import com.example.deean.medix.doktorovo.konsturktor_i_baza.DoktorLokalno;
import com.example.deean.medix.pacijentovo.konstruktor_i_baza.Pacijent;
import com.example.deean.medix.R;
import com.example.deean.medix.doktorovo.ToolbarActivity;
import com.github.ybq.android.spinkit.SpinKitView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecycleViewPacijenata extends ToolbarActivity implements View.OnClickListener {

    public String tekst;
    Button bPretraga;
    SpinKitView skv;
    EditText etPretraga;

    private ArrayList<Pacijent> spremi;
    private List<Pacijent> pacijents;
    private RecyclerView rv;

    private Doktor doktor;
    com.example.deean.medix.doktorovo.konsturktor_i_baza.DoktorLokalno DoktorLokalno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view_pacijenata);

        rv = (RecyclerView) findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        etPretraga = (EditText) findViewById(R.id.etPretraga);
        skv = (SpinKitView) findViewById(R.id.loading_view);
        bPretraga = (Button) findViewById(R.id.bPretraga);

        bPretraga.setOnClickListener(this);

        DoktorLokalno = new DoktorLokalno(this);

        doktor = DoktorLokalno.getPrijavljenogDoktora();
        postaviDrawer(postaviToolbar("Pacijenti u bazi"),doktor.getIme().toUpperCase(),doktor.getPrezime().toUpperCase(),doktor.getEmail(),doktor.getSpol()).build();

        initializeData();
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bPretraga:
                bPretraga.setVisibility(View.GONE);
                skv.setVisibility(View.VISIBLE);
                initializeData();
                break;
        }
    }
    private void initializeData(){
        bPretraga.setVisibility(View.GONE);
        skv.setVisibility(View.VISIBLE);
        tekst = String.valueOf(etPretraga.getText());
        spremi = new ArrayList<>();
        pacijents = new ArrayList<>();
        PacijenteDohvatiAPI.Factory.getIstance().response().enqueue(new Callback<ArrayList<Pacijent>>() {
            @Override
            public void onResponse(Call<ArrayList<Pacijent>> call, Response<ArrayList<Pacijent>> response) {
                bPretraga.setVisibility(View.VISIBLE);
                skv.setVisibility(View.GONE);
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
                bPretraga.setVisibility(View.VISIBLE);
                skv.setVisibility(View.GONE);
                Log.e("Failed", t.getMessage());
            }
        });
    }
    private void initializeAdapter(){
        RVAdapterPacijenata adapter = new RVAdapterPacijenata(RecycleViewPacijenata.this,pacijents);
        rv.setAdapter(adapter);
    }
}
