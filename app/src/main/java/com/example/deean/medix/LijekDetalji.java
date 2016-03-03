package com.example.deean.medix;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LijekDetalji extends AppCompatActivity {
    TextView imeLijeka,sastav,namjena,pakovanje,imeIAdresa;
    ImageView ivLijek;
    int [] poljeSlika = {R.drawable.lupocet_100,R.drawable.neofen_100,R.drawable.naklofen_100};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lijek_detalji);
        Intent intent = getIntent();
        String ime = intent.getStringExtra("ime1");
        Log.e("TAG",ime);

        imeLijeka = (TextView) findViewById(R.id.tvImeLijeka);
        imeLijeka.setText(ime);
        sastav = (TextView) findViewById(R.id.etSastav);
        namjena = (TextView) findViewById(R.id.etNamjena);
        pakovanje = (TextView) findViewById(R.id.etPakovanje);
        imeIAdresa = (TextView) findViewById(R.id.etImeIAdresa);
        ivLijek = (ImageView) findViewById(R.id.ivLijek);
        if(ime.equals("Lupocet 500mg tablete")){
            ivLijek.setImageResource(poljeSlika[0]);
        }
        else if(ime.equals("Naklofen")){
            ivLijek.setImageResource(poljeSlika[2]);
        }
        else if(ime.equals("Neofen")){
            ivLijek.setImageResource(poljeSlika[1]);
        }

        LijekDetaljiAPI.Factory.getIstance().response(ime).enqueue(new Callback<ArrayList<Lijek>>() { //u response ime
            @Override
            public void onResponse(Call<ArrayList<Lijek>> call, Response<ArrayList<Lijek>> response) {
                sastav.setText(response.body().get(0).getSastav());
                namjena.setText(response.body().get(0).getNamjena());
                pakovanje.setText(response.body().get(0).getPakovanje());
                imeIAdresa.setText(response.body().get(0).getIme_i_adresa_proizvodaca());



            }

            @Override
            public void onFailure(Call<ArrayList<Lijek>> call, Throwable t) {
                    Log.e("FAIL", "Nije dohvatilo");
                    Log.e("TAG", t.getMessage());
            }
        });
    }

}
