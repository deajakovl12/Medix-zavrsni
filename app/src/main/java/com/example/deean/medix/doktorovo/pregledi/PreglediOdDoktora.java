package com.example.deean.medix.doktorovo.pregledi;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.deean.medix.R;
import com.example.deean.medix.doktorovo.ToolbarActivity;
import com.example.deean.medix.doktorovo.konsturktor_i_baza.DoktorLokalno;
import com.example.deean.medix.doktorovo.konsturktor_i_baza.Doktor;
import com.example.deean.medix.doktorovo.pacijenti_u_bazi.RVAdapterPacijenata;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreglediOdDoktora extends ToolbarActivity implements View.OnClickListener {
    private Doktor doktor;
    DoktorLokalno doktorLokalno;

    public String tekst;
    Button bPretraga;
    EditText etPretraga;

    ImageView ivDodaj;

    private ArrayList<Pregledi> spremi;
    private List<Pregledi> pregledis;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregledi_od_doktora);

        rv = (RecyclerView) findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        etPretraga = (EditText) findViewById(R.id.etPretraga);
        bPretraga = (Button) findViewById(R.id.bPretraga);
        ivDodaj = (ImageView) findViewById(R.id.ivDodaj);


        bPretraga.setOnClickListener(this);
        ivDodaj.setOnClickListener(this);

        doktorLokalno = new DoktorLokalno(this);
        doktor = doktorLokalno.getPrijavljenogDoktora();
        postaviDrawer(postaviToolbar("Zakazani pregledi"),doktor.getIme().toUpperCase(),doktor.getPrezime().toUpperCase(),doktor.getEmail()).build();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bPretraga:
                initializeData();
                break;
            case R.id.ivDodaj:
                //startActivity(new Intent(this, RecycleViewPacijenata.class));
                break;

        }
    }
    private void initializeData() {
        tekst = String.valueOf(etPretraga.getText());
        spremi = new ArrayList<>();
        pregledis = new ArrayList<>();
        PregledAPI.Factory.getIstance().response(doktor.getId_doktor()).enqueue(new Callback<ArrayList<Pregledi>>() {
            @Override
            public void onResponse(Call<ArrayList<Pregledi>> call, Response<ArrayList<Pregledi>> response) {
                initializeAdapter();
                for (int i = 0; i < response.body().size(); i++) {
                    spremi.add(new Pregledi(response.body().get(i).getIme(), response.body().get(i).getPrezime(), response.body().get(i).getOib(), response.body().get(i).getDatum_pregleda(), response.body().get(i).getKomentar()));
//                    Log.e("DATUM: ", response.body().get(i).getDatum().toString() + " test" );

                }
                for (int i = 0; i < spremi.size(); i++) {
                    if (spremi.get(i).getIme().toLowerCase().contains(tekst.toLowerCase()) || spremi.get(i).getPrezime().toLowerCase().contains(tekst.toLowerCase()) || spremi.get(i).getOib().contains(tekst.toLowerCase())) {
                        pregledis.add(new Pregledi(spremi.get(i).getIme(), spremi.get(i).getPrezime(), spremi.get(i).getOib(), spremi.get(i).getDatum_pregleda(), spremi.get(i).getKomentar()));
                    }
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Pregledi>> call, Throwable t) {
                Log.e("Failed", t.getMessage());
            }
        });
    }
    private void initializeAdapter(){
        RVAdapterPregleda  adapter = new RVAdapterPregleda(PreglediOdDoktora.this,pregledis);
        rv.setAdapter(adapter);
    }

}