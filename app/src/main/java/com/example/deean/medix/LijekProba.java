package com.example.deean.medix;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LijekProba extends AppCompatActivity implements View.OnClickListener {
    TextView etNaziv,etSastav, etNamjena, etPakovanje,etIme_i_adresa_proizvodaca,etKada_ne_smije_primjeniti,etDoziranje,etNuspojave;
    Button bDohvati;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lijek_proba);


        etNaziv = (TextView) findViewById(R.id.etNaziv);
        etSastav = (TextView) findViewById(R.id.etSastav);
        etNamjena = (TextView) findViewById(R.id.etNamjena);
        etPakovanje = (TextView) findViewById(R.id.etPakovanje);
        etIme_i_adresa_proizvodaca = (TextView) findViewById(R.id.etIme_i_adresa_proizvodaca);
        etKada_ne_smije_primjeniti = (TextView) findViewById(R.id.etKada_ne_smije_primjeniti);
        etDoziranje = (TextView) findViewById(R.id.etDoziranje);
        etNuspojave = (TextView) findViewById(R.id.etNuspojave);
        bDohvati = (Button) findViewById(R.id.bDohvati);

        bDohvati.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        LijekAPI.Factory.getIstance().response().enqueue(new Callback<ArrayList<Lijek>>() {
            @Override
            public void onResponse(Call<ArrayList<Lijek>> call, Response<ArrayList<Lijek>> response) {
                    etNaziv.setText(response.body().get(0).getNaziv());
                    Log.i("TAG", response.body().get(0).getNaziv());
            }

            @Override
            public void onFailure(Call<ArrayList<Lijek>> call, Throwable t) {
                Log.e("Failed", t.getMessage());

            }
        });

    }

}
