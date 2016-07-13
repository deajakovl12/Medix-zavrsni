package com.example.deean.medix.doktorovo.pacijenti_od_doktora.detalji_o_pacijentu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.deean.medix.R;
import com.example.deean.medix.doktorovo.ToolbarActivity;
import com.example.deean.medix.doktorovo.konsturktor_i_baza.Doktor;
import com.example.deean.medix.doktorovo.konsturktor_i_baza.DoktorLokalno;
import com.example.deean.medix.lijekovi.Lijek;
import com.example.deean.medix.lijekovi.LijekAPI;
import com.example.deean.medix.lijekovi.RVAdapter;
import com.example.deean.medix.lijekovi.RecycleView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DodajLijekPacijentu extends ToolbarActivity implements View.OnClickListener {
    private Doktor doktor;
    DoktorLokalno doktorLokalno;

    Button bPretraga;
    EditText etPretraga;

    private ArrayList<Lijek> spremi;
    private List<Lijek> lijeks;
    private RecyclerView rv;

    private String tekst1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_lijek_pacijentu);


        rv = (RecyclerView) findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        etPretraga = (EditText) findViewById(R.id.etPretraga);
        bPretraga = (Button) findViewById(R.id.bPretraga);

        bPretraga.setOnClickListener(this);

        doktorLokalno = new DoktorLokalno(this);
        doktor = doktorLokalno.getPrijavljenogDoktora();

        postaviDrawer(postaviToolbar("Dodaj lijek pacijentu"), doktor.getIme().toUpperCase(), doktor.getPrezime().toUpperCase(), doktor.getEmail(),doktor.getSpol()).build();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bPretraga:
                initializeData();
                break;
        }
    }
    private void initializeData(){
        tekst1 = String.valueOf(etPretraga.getText());
        spremi = new ArrayList<>();
        lijeks = new ArrayList<>();
        DohvatiLijekoveKojeNeKoristiAPI.Factory.getIstance().response(OsobniPodaciFragment.id_pacijenta).enqueue(new Callback<ArrayList<Lijek>>() {
            @Override
            public void onResponse(Call<ArrayList<Lijek>> call, Response<ArrayList<Lijek>> response) {
                initializeAdapter();
                for (int i = 0; i < response.body().size(); i++) {
                    spremi.add(new Lijek(response.body().get(i).getNaziv(),response.body().get(i).getSlika_lijeka()));
                }
                for (int i = 0; i < spremi.size(); i++) {
                    if (spremi.get(i).getNaziv().toLowerCase().contains(tekst1.toLowerCase())) {
                        lijeks.add(new Lijek(spremi.get(i).getNaziv(),spremi.get(i).getSlika_lijeka() ));
                    }
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Lijek>> call, Throwable t) {
                Log.e("Failed", t.getMessage());
            }
        });
    }
    private void initializeAdapter(){
        RVAdapterDodajLijekPacijentu adapter = new RVAdapterDodajLijekPacijentu(this,lijeks);
        rv.setAdapter(adapter);
    }


}
