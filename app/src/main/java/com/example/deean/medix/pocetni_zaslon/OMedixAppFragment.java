package com.example.deean.medix.pocetni_zaslon;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.deean.medix.R;

import me.biubiubiu.justifytext.library.JustifyTextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class OMedixAppFragment extends Fragment {

    JustifyTextView oapp;


    public OMedixAppFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LayoutInflater lf = getActivity().getLayoutInflater();
        View view = lf.inflate(R.layout.fragment_omedix_app, container, false);
        // Inflate the layout for this fragment
        Login.bPrijava.setVisibility(View.INVISIBLE);
        Login.etEmail.setVisibility(View.INVISIBLE);
        Login.etLozinka.setVisibility(View.INVISIBLE);
        Login.tvLozinkaLink.setVisibility(View.INVISIBLE);
        Login.tvRegistracijaLink.setVisibility(View.INVISIBLE);
        oapp = (JustifyTextView) view.findViewById(R.id.tvOapp);
        oapp.setText("   Medix je aplikacija koja je kreirana kao tema završnog rada.\n" +
                "   Glavna ideja aplikacije je da sam doktor ima evidenciju i plan rada na svoje mobitelu.\n" +
                "   Aplikacija omogućuje doktoru da dodaje nove pacijente, te vodi evidenciju o njima.\n" +
                "   Svakom pacijentu moguće je dodati lijekove, pregledati sve lijekove koje koristi, te zakazati termin sa pacijentom.\n" +
                "   Sve ove informacije se spremaju u online bazi podataka, pa prema tome i pacijent vidi promjene.\n" +
                "   Pacijent može vidjeti koje lijekove mu je doktor predložio, namjestiti vrijeme konzumiranja lijeka, te također vidjeti termine pregleda kod doktora.");
        return view;
    }

}
