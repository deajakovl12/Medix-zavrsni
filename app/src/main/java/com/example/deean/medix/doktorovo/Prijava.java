package com.example.deean.medix.doktorovo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deean.medix.doktorovo.konsturktor_i_baza.Doktor;
import com.example.deean.medix.doktorovo.konsturktor_i_baza.DoktorLokalno;
import com.example.deean.medix.R;
import com.example.deean.medix.pacijentovo.doktor_pacijenta.DohvatiDoktorovePodatkeAPI;
import com.example.deean.medix.pacijentovo.konstruktor_i_baza.Pacijent;
import com.example.deean.medix.pacijentovo.konstruktor_i_baza.PacijentLokalno;
import com.example.deean.medix.pocetni_zaslon.Login;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Prijava extends ToolbarActivity implements View.OnClickListener {
    TextView etRadnoVrijeme, etSavjet, etTelefon, etAdresa, etPrezime, etIme, etMobitel, etTitula, tvNoDoctor;
    ImageView ivDoktor, ivTelZvanje, ivMobZvanje, noDoctor;
    com.example.deean.medix.doktorovo.konsturktor_i_baza.DoktorLokalno DoktorLokalno;

    private Doktor doktor;

    private Pacijent pacijent;
    PacijentLokalno pacijentLokalno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prijava);


        etRadnoVrijeme = (TextView) findViewById(R.id.etRadnoVrijeme);
        etSavjet = (TextView) findViewById(R.id.etSavjet);
        etTelefon = (TextView) findViewById(R.id.etTelefon);
        etAdresa = (TextView) findViewById(R.id.etAdresa);
        etIme = (TextView) findViewById(R.id.etIme);
        etPrezime = (TextView) findViewById(R.id.etPrezime);
        etMobitel = (TextView) findViewById(R.id.etMobitel);
        etTitula = (TextView) findViewById(R.id.etTitula);
        ivDoktor = (ImageView) findViewById(R.id.ivDoktor);
        ivTelZvanje = (ImageView) findViewById(R.id.ivTelZvanje);
        ivMobZvanje = (ImageView) findViewById(R.id.ivMobZvanje);
        noDoctor = (ImageView) findViewById(R.id.noDoctor);
        tvNoDoctor = (TextView) findViewById(R.id.tvNemateDoktora);

        ivMobZvanje.setOnClickListener(this);
        ivTelZvanje.setOnClickListener(this);

        DoktorLokalno = new DoktorLokalno(this);
        pacijentLokalno = new PacijentLokalno(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("Blabla", "pokrenuto");
        if (provjera() == true) {
            prikaziDoktorovePodatke();
        } else {
            pacijent = pacijentLokalno.getPrijavljenogPacijenta();
            postaviDrawer(postaviToolbar("Doktor"), pacijent.getIme(), pacijent.getPrezime(), pacijent.getEmail()).build();
            if(pacijent.getId_doktor().equals("null")){
                noDoctor.setVisibility(View.VISIBLE);
                tvNoDoctor.setVisibility(View.VISIBLE);
            }
            else{
                dohvatiDoktorovePodatke();
            }
        }
    }

    @Override
    public void onBackPressed() {
    }

    private boolean provjera() {
        return DoktorLokalno.provjeriPrijavljenogDoktora();
    }

    private void dohvatiDoktorovePodatke() {
        Log.e("DOC I PAC: ", pacijent.getId_doktor() + " " + pacijent.getId_pacijent());
        DohvatiDoktorovePodatkeAPI.Factory.getIstance().response(pacijent.getId_doktor(), pacijent.getId_pacijent()).enqueue(new Callback<ArrayList<Doktor>>() {
            @Override
            public void onResponse(Call<ArrayList<Doktor>> call, Response<ArrayList<Doktor>> response) {
                Log.e("PROSLo", "DA");
                etIme.setText(response.body().get(0).getIme());
                etPrezime.setText(response.body().get(0).getPrezime());
                etTitula.setText(response.body().get(0).getTitula());
                etRadnoVrijeme.setText(response.body().get(0).getRadno_vrijeme());
                etSavjet.setText(response.body().get(0).getRad_savjetovalista());
                etAdresa.setText(response.body().get(0).getAdresa());
                etTelefon.setText(response.body().get(0).getTelefon());
                etMobitel.setText(response.body().get(0).getMobitel());
                if (response.body().get(0).getSpol().equals("Musko")) {
                    Picasso.with(getApplicationContext()).load(R.drawable.doctor_red).into(ivDoktor);
                } else if (response.body().get(0).getSpol().equals("Zensko")) {
                    Picasso.with(getApplicationContext()).load(R.drawable.doktorica_red).into(ivDoktor);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Doktor>> call, Throwable t) {
                Log.e("NIJE PROSLo", "NE");

            }
        });
    }

    private void prikaziDoktorovePodatke() {

        doktor = DoktorLokalno.getPrijavljenogDoktora();
        etRadnoVrijeme.setText(doktor.getRadno_vrijeme());
        etIme.setText(doktor.getIme().toUpperCase());
        etPrezime.setText(doktor.getPrezime().toUpperCase());
        etSavjet.setText(doktor.getRad_savjetovalista());
        etAdresa.setText(doktor.getAdresa());
        etTelefon.setText(doktor.getTelefon());
        etMobitel.setText(doktor.getMobitel());
        etTitula.setText(doktor.getTitula());

        ivTelZvanje.setVisibility(View.GONE);
        ivMobZvanje.setVisibility(View.GONE);


        if (doktor.getSpol().equals("Musko")) {
            Picasso.with(getApplicationContext()).load(R.drawable.doctor_red).into(ivDoktor);
        } else if (doktor.getSpol().equals("Zensko")) {
            Picasso.with(getApplicationContext()).load(R.drawable.doktorica_red).into(ivDoktor);
        }
        postaviDrawer(postaviToolbar("Doktor"), doktor.getIme().toUpperCase(), doktor.getPrezime().toUpperCase(), doktor.getEmail(), doktor.getSpol()).build();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Blabla", "unisteno");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("Blabla", "pauzirano");


    }

    @Override
    public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ivMobZvanje:
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + etMobitel.getText().toString()));
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
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
