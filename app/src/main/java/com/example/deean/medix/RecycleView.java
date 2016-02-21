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


public class RecycleView extends AppCompatActivity implements View.OnClickListener {
    public String tekst;
    Button bPretraga;
    EditText etPretraga;

    private ArrayList<String> spremi;
    private List<Lijek> lijeks;
    private RecyclerView rv;

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
        lijeks = new ArrayList<>();
        LijekAPI.Factory.getIstance().response().enqueue(new Callback<ArrayList<Lijek>>() {
            @Override
            public void onResponse(Call<ArrayList<Lijek>> call, Response<ArrayList<Lijek>> response) {
                for (int i = 0; i < response.body().size(); i++) {
                        spremi.add(response.body().get(i).getNaziv());

                }
                //Log.i("TAG", response.body().get(0).getNaziv());
                //Log.i("TAG2", spremi.get(0));
                //Log.i("VELICINA PRVO", String.valueOf(spremi.size()));
                // tu stavit provjeri da li se taj lijek treba ispisat il ne
                    if(spremi.get(0).toLowerCase().contains(tekst.toLowerCase())) {
                        lijeks.add(new Lijek(spremi.get(0), R.drawable.lupocet_100));
                    }
                    if(spremi.get(1).toLowerCase().contains(tekst.toLowerCase())) {
                        lijeks.add(new Lijek(spremi.get(1), R.drawable.neofen_100));
                    }
                    if(spremi.get(2).toLowerCase().contains(tekst.toLowerCase())) {
                        lijeks.add(new Lijek(spremi.get(2), R.drawable.naklofen_100));
                    }


                //Log.i("LIJEKS", String.valueOf(lijeks));
                initializeAdapter();
            }

            @Override
            public void onFailure(Call<ArrayList<Lijek>> call, Throwable t) {
                Log.e("Failed", t.getMessage());

            }
        });
    }
    private void initializeAdapter(){
        RVAdapter adapter = new RVAdapter(lijeks);
        rv.setAdapter(adapter);
    }


}
