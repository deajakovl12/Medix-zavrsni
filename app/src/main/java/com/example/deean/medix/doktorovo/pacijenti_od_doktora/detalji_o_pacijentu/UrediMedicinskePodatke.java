package com.example.deean.medix.doktorovo.pacijenti_od_doktora.detalji_o_pacijentu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.deean.medix.R;
import com.example.deean.medix.doktorovo.ToolbarActivity;
import com.example.deean.medix.doktorovo.konsturktor_i_baza.Doktor;
import com.example.deean.medix.doktorovo.konsturktor_i_baza.DoktorLokalno;
import com.example.deean.medix.doktorovo.pacijenti_u_bazi.PacijentuDajDoktoraAPI;
import com.example.deean.medix.doktorovo.pacijenti_u_bazi.PacijentuPromjeniMedicinskePodatke;
import com.example.deean.medix.doktorovo.pacijenti_u_bazi.RecycleViewPacijenata;
import com.example.deean.medix.pacijentovo.konstruktor_i_baza.Pacijent;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UrediMedicinskePodatke extends ToolbarActivity implements View.OnClickListener {
     EditText etBolesti, etLaboratorij;
     Button bSpremi;
     private Doktor doktor;
     DoktorLokalno doktorLokalno;
     MedicinskiPodaciFragment mpf = new MedicinskiPodaciFragment();

    public static String getBol() {
        return bol;
    }

    public static String getLab() {
        return lab;
    }

    static String bol, lab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uredi_medicinske_podatke);

        doktorLokalno = new DoktorLokalno(this);
        etBolesti = (EditText) findViewById(R.id.etUrediBolesti);
        etLaboratorij = (EditText) findViewById(R.id.etUrediLaboratorij);
        bSpremi = (Button) findViewById(R.id.bSpremi);
        bSpremi.setOnClickListener(this);


        etBolesti.setText(mpf.getBolest());
        etBolesti.setSelection(etBolesti.getText().length());

        etLaboratorij.setText(mpf.getLaboratorij());

        doktor = doktorLokalno.getPrijavljenogDoktora();
        postaviDrawer(postaviToolbar("Uredi medicinske podatke"), doktor.getIme().toUpperCase(), doktor.getPrezime().toUpperCase(), doktor.getEmail()).build();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bSpremi:

                promjeniPodatkePacijenta();

                break;
        }
    }
    private void promjeniPodatkePacijenta(){
        PacijentuPromjeniMedicinskePodatke.Factory.getIstance().response(mpf.getOib(),etBolesti.getText().toString(),etLaboratorij.getText().toString()).enqueue(new Callback<ArrayList<Void>>() {
            @Override
            public void onResponse(Call<ArrayList<Void>> call, Response<ArrayList<Void>> response) {
                Log.e("Proslo", doktor.getId_doktor());
            }
            @Override
            public void onFailure(Call<ArrayList<Void>> call, Throwable t) {
                Log.e("Nije proslo",doktor.getId_doktor());
                AlertDialog.Builder dialogBuilder1 = new AlertDialog.Builder(UrediMedicinskePodatke.this);
                dialogBuilder1.setTitle("Uspješno!");
                dialogBuilder1.setMessage("Promjenili ste medicinske podatke pacijentu!");
                dialogBuilder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        bol = etBolesti.getText().toString();
                        lab = etLaboratorij.getText().toString();
                        finish();

                    }
                });
                dialogBuilder1.show();
            }
        });

    }
}
