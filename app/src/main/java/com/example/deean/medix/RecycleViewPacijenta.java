package com.example.deean.medix;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecycleViewPacijenta extends ToolbarActivity implements View.OnClickListener {

    public String tekst;
    Button bPretraga;
    EditText etPretraga;

    private ArrayList<Pacijent> spremi;
    private List<Pacijent> pacijents;
    private RecyclerView rv;

    private Doktor doktor;
    DoktorLokalno DoktorLokalno;

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
        bPretraga.setOnClickListener(this);


        DoktorLokalno = new DoktorLokalno(this);

        doktor = DoktorLokalno.getPrijavljenogDoktora();
        postaviDrawer(postaviToolbar("Pacijenti"),doktor.ime.toUpperCase(),doktor.prezime.toUpperCase(),doktor.email).build();
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bPretraga:
                initializeData();
                break;
        }
    }
    private void initializeData(){
        tekst = String.valueOf(etPretraga.getText());
        //Log.i("TAG",tekst);
        spremi = new ArrayList<>();
        pacijents = new ArrayList<>();
        PacijentAPI.Factory.getIstance().response().enqueue(new Callback<ArrayList<Pacijent>>() {
            @Override
            public void onResponse(Call<ArrayList<Pacijent>> call, Response<ArrayList<Pacijent>> response) {
                initializeAdapter();
                for (int i = 0; i < response.body().size(); i++) {
                    //lijeks.add(new Lijek(response.body().get(i).getNaziv(),poljeSlika[i]));
                    spremi.add(new Pacijent(response.body().get(i).getIme(),response.body().get(i).getPrezime(),response.body().get(i).getAdresa(),response.body().get(i).getOib()));
                }
                //Log.i("TAG", responbodyse.body().get(0).getNaziv());
                //Log.i("TAG2", spremi.get(0));
                //Log.i("VELICINA PRVO", String.valueOf(spremi.size()));
                // napravit polje u kojem su slike i onda citat od tamo i tu upisivat
                for (int i = 0; i < spremi.size(); i++) {
                    if (spremi.get(i).getIme().toLowerCase().contains(tekst.toLowerCase()) || spremi.get(i).getPrezime().toLowerCase().contains(tekst.toLowerCase()) || spremi.get(i).getOib().contains(tekst.toLowerCase())) {
                        pacijents.add(new Pacijent(spremi.get(i).getIme(),spremi.get(i).getPrezime(),spremi.get(i).getAdresa(),spremi.get(i).getOib()));
                    }
                }
                //Log.i("LIJEKS", String.valueOf(lijeks));
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
