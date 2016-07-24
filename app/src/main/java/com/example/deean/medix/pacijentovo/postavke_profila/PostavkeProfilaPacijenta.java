package com.example.deean.medix.pacijentovo.postavke_profila;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.deean.medix.R;
import com.example.deean.medix.databinding.ActivityPostavkeProfilaPacijentaBinding;
import com.example.deean.medix.doktorovo.Prijava;
import com.example.deean.medix.doktorovo.ToolbarActivity;
import com.example.deean.medix.doktorovo.konsturktor_i_baza.Doktor;
import com.example.deean.medix.pacijentovo.Prijava_Pacijent;
import com.example.deean.medix.pacijentovo.konstruktor_i_baza.Pacijent;
import com.example.deean.medix.pacijentovo.konstruktor_i_baza.PacijentLokalno;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostavkeProfilaPacijenta extends ToolbarActivity {

    private Pacijent pacijent;
    PacijentLokalno pacijentLokalno;

    ActivityPostavkeProfilaPacijentaBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_postavke_profila_pacijenta);

        pacijentLokalno = new PacijentLokalno(this);
        pacijent = pacijentLokalno.getPrijavljenogPacijenta();

        bind.setPacijent(pacijent);
        popuniSpinner(bind.spinner);

        postaviDrawer(postaviToolbar("Postavke profila"), pacijent.getIme(), pacijent.getPrezime(), pacijent.getEmail()).build();
        bind.azurirajPodatke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                azurirajPodatke();
            }
        });
    }

    private void azurirajPodatke(){
        bind.azurirajPodatke.setVisibility(View.GONE);
        bind.loadingView.setVisibility(View.VISIBLE);
        AzurirajPodatkePacijentaAPI.Factory.getIstance().response(pacijent.getId_pacijent(),bind.etIme.getText().toString(),bind.etPrezime.getText().toString(),bind.etAdresa.getText().toString(),bind.etTelefon.getText().toString(),bind.etMobitel.getText().toString(),bind.spinner.getSelectedItem().toString()).enqueue(new Callback<ArrayList<Void>>() {
            @Override
            public void onResponse(Call<ArrayList<Void>> call, Response<ArrayList<Void>> response) {
                bind.azurirajPodatke.setVisibility(View.VISIBLE);
                bind.loadingView.setVisibility(View.GONE);

                Log.e("DA","DADADA");
            }

            @Override
            public void onFailure(Call<ArrayList<Void>> call, Throwable t) {
                bind.azurirajPodatke.setVisibility(View.VISIBLE);
                bind.loadingView.setVisibility(View.GONE);

                Log.e("NE", "NIJE USPIO");
                Pacijent pacijent1 = new Pacijent(pacijent.getId_pacijent(),bind.etIme.getText().toString(),bind.etPrezime.getText().toString(),bind.etAdresa.getText().toString(),pacijent.getOib(),pacijent.getLozinka(),bind.etTelefon.getText().toString(),pacijent.getEmail(),bind.spinner.getSelectedItem().toString(),bind.etMobitel.getText().toString(),pacijent.getBolesti(),pacijent.getLaboratorijski(),pacijent.getId_doktor());
                pacijentLokalno.spremiPacijentPodatke(pacijent1);
                AlertDialog.Builder dialogBuilder1 = new AlertDialog.Builder(PostavkeProfilaPacijenta.this);
                dialogBuilder1.setTitle("Uspješno!");
                dialogBuilder1.setMessage("Ažurirali ste podatke!");
                dialogBuilder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(PostavkeProfilaPacijenta.this, Prijava_Pacijent.class));
                    }
                });
                dialogBuilder1.show();

            }
        });
    }
    private void popuniSpinner(Spinner spinner) {
        List<String> lables = new ArrayList<String>();
        lables.add("Musko");
        lables.add("Zensko");

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lables);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setSelection(spinnerAdapter.getPosition(pacijent.getSpol()));
    }
}
