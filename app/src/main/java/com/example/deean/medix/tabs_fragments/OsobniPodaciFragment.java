package com.example.deean.medix.tabs_fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deean.medix.Lijek;
import com.example.deean.medix.LijekDetaljiAPI;
import com.example.deean.medix.Pacijent;
import com.example.deean.medix.PacijentDetaljiAPI;
import com.example.deean.medix.R;
import com.example.deean.medix.RecycleView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Deean on 4.3.2016..
 */
public class OsobniPodaciFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    TextView  etSpol, etTelefon, etAdresa,etPrezime,etIme,etMobitel,etOIB;
    ImageView ivTelZvanje, ivMobZvanje;
    public static String id_pacijenta;

    private static  final String ARG_EXAMPLE = "this_is_a_constant";
    private String oib;
    public OsobniPodaciFragment(){
    }
    public static OsobniPodaciFragment newIstance (String example_argument){
        OsobniPodaciFragment osobniPodaciFragment = new OsobniPodaciFragment();
        Bundle args = new Bundle();
        args.putString(ARG_EXAMPLE, example_argument);
        osobniPodaciFragment.setArguments(args);
        return osobniPodaciFragment;
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
        View view = lf.inflate(R.layout.activity_osobni_podaci_fragment, container, false);


        etSpol = (TextView) view.findViewById(R.id.etSpol);
        etTelefon = (TextView) view.findViewById(R.id.etTelefon);
        etAdresa = (TextView) view.findViewById(R.id.etAdresa);
        etIme = (TextView) view.findViewById(R.id.etIme);
        etPrezime = (TextView) view.findViewById(R.id.etPrezime);
        etMobitel = (TextView) view.findViewById(R.id.etMobitel);
        etOIB = (TextView) view.findViewById(R.id.etOIB);

        ivMobZvanje = (ImageView) view.findViewById(R.id.ivMobZvanje);
        ivTelZvanje = (ImageView) view.findViewById(R.id.ivTelZvanje);

        ivMobZvanje.setOnClickListener(this);
        ivTelZvanje.setOnClickListener(this);

        PacijentDetaljiAPI.Factory.getIstance().response(oib).enqueue(new Callback<ArrayList<Pacijent>>() { //u response ime
            @Override
            public void onResponse(Call<ArrayList<Pacijent>> call, Response<ArrayList<Pacijent>> response) {
                etSpol.setText(response.body().get(0).getSpol());
                etTelefon.setText(response.body().get(0).getTelefon());
                etAdresa.setText(response.body().get(0).getAdresa());
                etIme.setText(response.body().get(0).getIme());
                etPrezime.setText(response.body().get(0).getPrezime());
                etMobitel.setText(response.body().get(0).getMobitel());
                etOIB.setText(response.body().get(0).getOib());
                id_pacijenta=response.body().get(0).getId_pacijent();
                Log.e("ID",id_pacijenta);

            }
            @Override
            public void onFailure(Call<ArrayList<Pacijent>> call, Throwable t) {
                Log.e("FAIL", "Nije dohvatilo");
                Log.e("TAG", t.getMessage());
            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.ivMobZvanje:
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + etMobitel.getText().toString()));
                startActivity(callIntent);
                break;

            case R.id.ivTelZvanje:
                Intent telefonIntent = new Intent(Intent.ACTION_CALL);
                telefonIntent.setData(Uri.parse("tel:" + etTelefon.getText().toString()));
                startActivity(telefonIntent);
                break;
        }


        }
}
