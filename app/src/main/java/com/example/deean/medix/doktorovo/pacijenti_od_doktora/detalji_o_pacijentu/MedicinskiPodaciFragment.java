package com.example.deean.medix.doktorovo.pacijenti_od_doktora.detalji_o_pacijentu;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.deean.medix.doktorovo.pacijenti_u_bazi.RecycleViewPacijenata;
import com.example.deean.medix.pacijentovo.konstruktor_i_baza.Pacijent;
import com.example.deean.medix.R;
import com.example.deean.medix.pocetni_zaslon.Login;

import java.util.ArrayList;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Deean on 4.3.2016..
 */
public class MedicinskiPodaciFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    TextView  etbolesti, etlaboratorijski;
    Button uredi;

    public String getBolest() {
        return bolest;
    }


    public String getLaboratorij() {
        return laboratorij;
    }


    static String bolest, laboratorij;


    private static  final String ARG_EXAMPLE = "this_is_a_constant";

    public String getOib() {
        return oib;
    }

    private static String oib;

    public MedicinskiPodaciFragment(){
    }
    public static MedicinskiPodaciFragment newIstance (String example_argument){
        MedicinskiPodaciFragment medicinskiPodaciFragment = new MedicinskiPodaciFragment();
        Bundle args = new Bundle();
        args.putString(ARG_EXAMPLE, example_argument);
        medicinskiPodaciFragment.setArguments(args);
        return medicinskiPodaciFragment;
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
        View view = lf.inflate(R.layout.activity_medicinski_podaci_fragment, container, false);


        etbolesti = (TextView) view.findViewById(R.id.etbolesti);
        etlaboratorijski = (TextView) view.findViewById(R.id.etlaboratorijski);
        uredi = (Button) view.findViewById(R.id.bUredi);

        uredi.setOnClickListener(this);

        //FragmentTransaction ft = getFragmentManager().beginTransaction();
        //ft.detach(this).attach(this).commit();


        dohvati();
        return view;
    }
    private void dohvati(){
        PacijentDetaljiAPI.Factory.getIstance().response(oib).enqueue(new Callback<ArrayList<Pacijent>>() { //u response ime
            @Override
            public void onResponse(Call<ArrayList<Pacijent>> call, Response<ArrayList<Pacijent>> response) {
                etbolesti.setText(response.body().get(0).getBolesti());
                etlaboratorijski.setText(response.body().get(0).getLaboratorijski());
                bolest = etbolesti.getText().toString();
                laboratorij = etlaboratorijski.getText().toString();
            }
            @Override
            public void onFailure(Call<ArrayList<Pacijent>> call, Throwable t) {
                Log.e("FAIL", "Nije dohvatilo");
                Log.e("TAG", t.getMessage());
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bUredi:
                startActivity(new Intent(getContext(),UrediMedicinskePodatke.class));
                break;
        }

}
    @Override
    public void onResume() {
        super.onResume();
        Log.i("Refresh","refreshalo");
        UrediMedicinskePodatke ump = new UrediMedicinskePodatke();
        etbolesti.setText(ump.getBol());
        etlaboratorijski.setText(ump.getLab());
        bolest = etbolesti.getText().toString();
        laboratorij = etlaboratorijski.getText().toString();
    }

}
