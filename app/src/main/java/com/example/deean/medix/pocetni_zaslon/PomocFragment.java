package com.example.deean.medix.pocetni_zaslon;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.deean.medix.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PomocFragment extends Fragment {


    TextView zapoceti, registrirati, zaboraviti;

    public PomocFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LayoutInflater lf = getActivity().getLayoutInflater();
        View view = lf.inflate(R.layout.fragment_pomoc, container, false);

        Login.bPrijava.setVisibility(View.INVISIBLE);
        Login.etEmail.setVisibility(View.INVISIBLE);
        Login.etLozinka.setVisibility(View.INVISIBLE);
        Login.tvLozinkaLink.setVisibility(View.INVISIBLE);
        Login.tvRegistracijaLink.setVisibility(View.INVISIBLE);

        zapoceti = (TextView) view.findViewById(R.id.tvzapocetirad);
        registrirati = (TextView) view.findViewById(R.id.tvregistriratise);
        zaboraviti = (TextView) view.findViewById(R.id.tvzaboraviti);
        zapoceti.setText("Kako bi mogli koristiti funkcionalnosti aplikacije potrebno je izraditi račun!");
        registrirati.setText("Postoji registracija za pacijenta i doktora. \n" +
                             "Kako bi započeli s registracijom stisnemo na Registriraj se ovdje. \n" +
                             "U novom prozoru odabiremo kakav račun želimo kreirati, važno je ispuniti sve podatke.");
        zaboraviti.setText("Iznad samog dugma za prijavu nalazi se Zaboravili ste na lozinku? \n" +
                           "Unesite svoj e-mail s kojim ste kreirali račun.\n" +
                           "Provjeriti svoj e-mail kako bi dobili novu lozinku!" );
        return view;
    }

}
