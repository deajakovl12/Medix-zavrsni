package com.example.deean.medix.lijekovi.detalji_o_lijeku;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deean.medix.R;
import com.example.deean.medix.lijekovi.Lijek;
import com.example.deean.medix.lijekovi.RecycleView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Deean on 4.3.2016..
 */
public class OsnovneInformacijeFragment extends android.support.v4.app.Fragment{

    private static  final String ARG_EXAMPLE = "this_is_a_constant";
    private String ime;
    TextView imeLijeka,sastav,namjena,pakovanje,imeIAdresa;
    ImageView ivLijek;
    //int [] poljeSlika = {R.drawable.lupocet_100,R.drawable.neofen_100,R.drawable.naklofen_100};


    public OsnovneInformacijeFragment(){
    }
    public static OsnovneInformacijeFragment newIstance (String example_argument){
        OsnovneInformacijeFragment osnovneInformacijeFragment = new OsnovneInformacijeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_EXAMPLE, example_argument);
        osnovneInformacijeFragment.setArguments(args);
        return osnovneInformacijeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ime = getArguments().getString(ARG_EXAMPLE);
        Log.e("Fragment created with", ime);

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LayoutInflater lf = getActivity().getLayoutInflater();
        View view = lf.inflate(R.layout.fragment_osnovne_informacije, container, false);

        imeLijeka = (TextView) view.findViewById(R.id.tvImeLijeka);
        imeLijeka.setText(ime);
        sastav = (TextView) view.findViewById(R.id.etSastav);
        namjena = (TextView) view.findViewById(R.id.etNamjena);
        pakovanje = (TextView) view.findViewById(R.id.etPakovanje);
        imeIAdresa = (TextView) view.findViewById(R.id.etImeIAdresa);
        ivLijek = (ImageView) view.findViewById(R.id.ivLijek);


        /*if(ime.equals("Lupocet 500mg tablete")){
            ivLijek.setImageResource(RecycleView.poljeSlika[0]);
        }
        else if(ime.equals("Naklofen")){
            ivLijek.setImageResource(RecycleView.poljeSlika[1]);
        }
        else if(ime.equals("Neofen")){
            ivLijek.setImageResource(RecycleView.poljeSlika[2]);
        }*/


        LijekDetaljiAPI.Factory.getIstance().response(ime).enqueue(new Callback<ArrayList<Lijek>>() { //u response ime
            @Override
            public void onResponse(Call<ArrayList<Lijek>> call, Response<ArrayList<Lijek>> response) {
                sastav.setText(response.body().get(0).getSastav());
                namjena.setText(response.body().get(0).getNamjena());
                pakovanje.setText(response.body().get(0).getPakovanje());
                imeIAdresa.setText(response.body().get(0).getIme_i_adresa_proizvodaca());
                Picasso.with(getContext()).load(response.body().get(0).getSlika_lijeka()).into(ivLijek);
            }
            @Override
            public void onFailure(Call<ArrayList<Lijek>> call, Throwable t) {
                Log.e("FAIL", "Nije dohvatilo");
                Log.e("TAG", t.getMessage());
            }
        });
        return view;
        //return inflater.inflate(R.layout.fragment_osnovne_informacije,container,false);

    }
}
