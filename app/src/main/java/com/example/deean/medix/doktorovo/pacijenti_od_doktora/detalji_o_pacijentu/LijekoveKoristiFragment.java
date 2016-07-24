package com.example.deean.medix.doktorovo.pacijenti_od_doktora.detalji_o_pacijentu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.deean.medix.lijekovi.Lijek;
import com.example.deean.medix.R;
import com.example.deean.medix.lijekovi.RVAdapter;
import com.example.deean.medix.lijekovi.RecycleView;
import com.github.ybq.android.spinkit.SpinKitView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Deean on 4.3.2016..
 */
public class LijekoveKoristiFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    private static  final String ARG_EXAMPLE = "this_is_a_constant";

    private String oib;

    public String getOib() {
        return oib;
    }

    public String tekst;
    Button bPretraga;
    SpinKitView skv;
    EditText etPretraga;

    ImageView ivDodaj;


    private ArrayList<Lijek> spremi;
    private List<Lijek> lijeks;
    private RecyclerView rv;


    public LijekoveKoristiFragment(){
    }
    public static LijekoveKoristiFragment newIstance (String example_argument){
        LijekoveKoristiFragment lijekoveKoristiFragment = new LijekoveKoristiFragment();
        Bundle args = new Bundle();
        args.putString(ARG_EXAMPLE, example_argument);
        lijekoveKoristiFragment.setArguments(args);
        return lijekoveKoristiFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        oib = getArguments().getString(ARG_EXAMPLE);
        Log.i("Fragment created with", oib);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LayoutInflater lf = getActivity().getLayoutInflater();
        View view = lf.inflate(R.layout.activity_lijekove_koristi_fragment, container, false);

        rv = (RecyclerView) view.findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        etPretraga = (EditText) view.findViewById(R.id.etPretraga);
        bPretraga = (Button) view.findViewById(R.id.bPretraga);
        skv = (SpinKitView) view.findViewById(R.id.loading_view);
        ivDodaj = (ImageView) view.findViewById(R.id.ivDodaj);

        bPretraga.setOnClickListener(this);
        ivDodaj.setOnClickListener(this);
        Log.e("ID", OsobniPodaciFragment.id_pacijenta);

        initializeData();


        return view;
    }

    @Override
    public void onClick(View v) {
            switch(v.getId()) {
                case R.id.bPretraga:
                    bPretraga.setVisibility(View.GONE);
                    skv.setVisibility(View.VISIBLE);
                    initializeData();
                    break;
                case R.id.ivDodaj:
                    startActivity(new Intent(getContext(),DodajLijekPacijentu.class));
                    break;

            }

    }
    private void initializeData(){
        bPretraga.setVisibility(View.GONE);
        skv.setVisibility(View.VISIBLE);
        tekst = String.valueOf(etPretraga.getText());
        spremi = new ArrayList<>();
        lijeks = new ArrayList<>();
        LijekoviPacijentaAPI.Factory.getIstance().response(OsobniPodaciFragment.id_pacijenta).enqueue(new Callback<ArrayList<Lijek>>() {
            @Override
            public void onResponse(Call<ArrayList<Lijek>> call, Response<ArrayList<Lijek>> response) {
                bPretraga.setVisibility(View.VISIBLE);
                skv.setVisibility(View.GONE);
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
                bPretraga.setVisibility(View.VISIBLE);
                skv.setVisibility(View.GONE);
                Log.e("Failed", t.getMessage());
            }
        });
    }
    private void initializeAdapter(){
        RVAdapter adapter = new RVAdapter(getActivity(),lijeks);
        rv.setAdapter(adapter);
    }
}
