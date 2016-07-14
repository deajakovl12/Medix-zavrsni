package com.example.deean.medix.lijekovi;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.deean.medix.doktorovo.konsturktor_i_baza.Doktor;
import com.example.deean.medix.doktorovo.konsturktor_i_baza.DoktorLokalno;
import com.example.deean.medix.R;
import com.example.deean.medix.doktorovo.ToolbarActivity;
import com.example.deean.medix.doktorovo.pacijenti_od_doktora.detalji_o_pacijentu.LijekoviPacijentaAPI;
import com.example.deean.medix.pacijentovo.konstruktor_i_baza.Pacijent;
import com.example.deean.medix.pacijentovo.konstruktor_i_baza.PacijentLokalno;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecycleView extends ToolbarActivity implements View.OnClickListener {
    public String tekst;
    Button bPretraga;
    EditText etPretraga;


    private ArrayList<Lijek> spremi;
    private List<Lijek> lijeks;
    private RecyclerView rv;

    //public static int [] poljeSlika = {R.drawable.lupocet_100,R.drawable.naklofen_100,R.drawable.neofen_100};

    private Doktor doktor;
    com.example.deean.medix.doktorovo.konsturktor_i_baza.DoktorLokalno DoktorLokalno;
    private Pacijent pacijent;
    PacijentLokalno pacijentLokalno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);

        rv = (RecyclerView) findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        etPretraga = (EditText) findViewById(R.id.etPretraga);
        bPretraga = (Button) findViewById(R.id.bPretraga);
        bPretraga.setOnClickListener(this);


        DoktorLokalno = new DoktorLokalno(this);
        pacijentLokalno = new PacijentLokalno(this);

        doktor = DoktorLokalno.getPrijavljenogDoktora();
        pacijent = pacijentLokalno.getPrijavljenogPacijenta();

        if(DoktorLokalno.provjeriPrijavljenogDoktora()) {
            postaviDrawer(postaviToolbar("Lijekovi"), doktor.getIme().toUpperCase(), doktor.getPrezime().toUpperCase(), doktor.getEmail(), doktor.getSpol()).build();
            initializeData();
        }
        else if(pacijentLokalno.provjeriPrijavljenogPacijenta()){
            postaviDrawer(postaviToolbar("Dobiveni lijekovi"),pacijent.getIme(),pacijent.getPrezime(),pacijent.getEmail()).build();
            initializeData2();
        }

    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bPretraga:
                if(DoktorLokalno.provjeriPrijavljenogDoktora()) {
                    initializeData();
                }
                else if(pacijentLokalno.provjeriPrijavljenogPacijenta()){
                    initializeData2();
                }
                break;
        }
    }
    private void initializeData(){
        tekst = String.valueOf(etPretraga.getText());
        //Log.i("TAG",tekst);
        spremi = new ArrayList<>();
        lijeks = new ArrayList<>();
        LijekAPI.Factory.getIstance().response().enqueue(new Callback<ArrayList<Lijek>>() {
            @Override
            public void onResponse(Call<ArrayList<Lijek>> call, Response<ArrayList<Lijek>> response) {
                initializeAdapter();
                for (int i = 0; i < response.body().size(); i++) {
                    //lijeks.add(new Lijek(response.body().get(i).getNaziv(),poljeSlika[i]));
                    spremi.add(new Lijek(response.body().get(i).getNaziv(),response.body().get(i).getSlika_lijeka()));
                }
                for (int i = 0; i < spremi.size(); i++) {
                    if (spremi.get(i).getNaziv().toLowerCase().contains(tekst.toLowerCase())) {
                        lijeks.add(new Lijek(spremi.get(i).getNaziv(), spremi.get(i).getSlika_lijeka()));
                    }
                }
                //Log.i("LIJEKS", String.valueOf(lijeks));
            }

            @Override
            public void onFailure(Call<ArrayList<Lijek>> call, Throwable t) {
                Log.e("Failed", t.getMessage());
            }
        });
    }
    private void initializeData2(){
        tekst = String.valueOf(etPretraga.getText());
        //Log.i("TAG",tekst);
        spremi = new ArrayList<>();
        lijeks = new ArrayList<>();
        LijekoviPacijentaAPI.Factory.getIstance().response(pacijent.getId_pacijent()).enqueue(new Callback<ArrayList<Lijek>>() {
            @Override
            public void onResponse(Call<ArrayList<Lijek>> call, Response<ArrayList<Lijek>> response) {
                initializeAdapter();
                for (int i = 0; i < response.body().size(); i++) {
                    spremi.add(new Lijek(response.body().get(i).getNaziv(),response.body().get(i).getSlika_lijeka()));
                }
                for (int i = 0; i < spremi.size(); i++) {
                    if (spremi.get(i).getNaziv().toLowerCase().contains(tekst.toLowerCase())) {
                        lijeks.add(new Lijek(spremi.get(i).getNaziv(), spremi.get(i).getSlika_lijeka()));
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
        RVAdapter adapter = new RVAdapter(RecycleView.this,lijeks);
        rv.setAdapter(adapter);
    }
}
