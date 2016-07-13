package com.example.deean.medix.doktorovo.postavke_profila;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.deean.medix.R;
import com.example.deean.medix.doktorovo.Prijava;
import com.example.deean.medix.doktorovo.ToolbarActivity;
import com.example.deean.medix.doktorovo.konsturktor_i_baza.Doktor;
import com.example.deean.medix.doktorovo.konsturktor_i_baza.DoktorLokalno;
import com.example.deean.medix.doktorovo.pregledi.PreglediOdDoktora;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostavkeProfilaDoktora extends ToolbarActivity implements View.OnClickListener {

    Doktor doktor;
    DoktorLokalno doktorLokalno;
    EditText etIme, etPrezime, etTitula, etRadnoVrijeme, etRadSavjetovalista, etAdresa, etTelefon, etMobitel;
    Button azurirajPodatke;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postavke_profila_doktora);
        doktorLokalno = new DoktorLokalno(this);
        doktor = doktorLokalno.getPrijavljenogDoktora();
        etIme = (EditText) findViewById(R.id.etIme);
        etPrezime = (EditText) findViewById(R.id.etPrezime);
        etTitula = (EditText) findViewById(R.id.etTitula);
        etRadnoVrijeme = (EditText) findViewById(R.id.etRadnoVrijeme);
        etRadSavjetovalista = (EditText) findViewById(R.id.etRadSavjetovalista);
        etAdresa = (EditText) findViewById(R.id.etAdresa);
        etTelefon = (EditText) findViewById(R.id.etTelefon);
        etMobitel = (EditText) findViewById(R.id.etMobitel);
        azurirajPodatke = (Button) findViewById(R.id.azurirajPodatke);
        spinner = (Spinner) findViewById(R.id.spinner);


        azurirajPodatke.setOnClickListener(this);
        postaviDrawer(postaviToolbar("Postavke profila"),doktor.getIme().toUpperCase(),doktor.getPrezime().toUpperCase(),doktor.getEmail(),doktor.getSpol()).build();
        prikaziPodatkePrijavjenogDoktora();
        etIme.setSelection(etIme.getText().length());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.azurirajPodatke:
                spremiPodatkeODoktoru();
                break;
        }
    }
    private void prikaziPodatkePrijavjenogDoktora(){
        etRadnoVrijeme.setText(doktor.getRadno_vrijeme());
        etIme.setText(doktor.getIme());
        etPrezime.setText(doktor.getPrezime());
        etRadSavjetovalista.setText(doktor.getRad_savjetovalista());
        etAdresa.setText(doktor.getAdresa());
        etTelefon.setText(doktor.getTelefon());
        etMobitel.setText(doktor.getMobitel());
        etTitula.setText(doktor.getTitula());
        popuniSpinner();
    }
    private void spremiPodatkeODoktoru(){
        AzurirajPodatkeDoktora.Factory.getIstance().response(doktor.getId_doktor(),etIme.getText().toString(),etPrezime.getText().toString(),etTitula.getText().toString(),etRadnoVrijeme.getText().toString(),etRadSavjetovalista.getText().toString(),etAdresa.getText().toString(),etTelefon.getText().toString(),etMobitel.getText().toString(),spinner.getSelectedItem().toString()).enqueue(new Callback<ArrayList<Integer>>() {
            @Override
            public void onResponse(Call<ArrayList<Integer>> call, Response<ArrayList<Integer>> response) {
                Log.e("Uspjeh", "uspio");
            }

            @Override
            public void onFailure(Call<ArrayList<Integer>> call, Throwable t) {
                Log.e("NE", "NIJE USPIO");
                Doktor doktor1 = new Doktor(doktor.getId_doktor(),etIme.getText().toString(),etPrezime.getText().toString(),etAdresa.getText().toString(),doktor.getOib(),doktor.getLozinka(),etTelefon.getText().toString(),doktor.getEmail(),etRadnoVrijeme.getText().toString(),etRadSavjetovalista.getText().toString(),etMobitel.getText().toString(),etTitula.getText().toString(),spinner.getSelectedItem().toString());
                doktorLokalno.spremiDoktorPodatke(doktor1);
                AlertDialog.Builder dialogBuilder1 = new AlertDialog.Builder(PostavkeProfilaDoktora.this);
                dialogBuilder1.setTitle("Uspješno!");
                dialogBuilder1.setMessage("Ažurirali ste podatke!");
                dialogBuilder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(PostavkeProfilaDoktora.this, Prijava.class));
                    }
                });
                dialogBuilder1.show();


            }
        });
    }

    private void popuniSpinner() {
        List<String> lables = new ArrayList<String>();
        lables.add("Musko");
        lables.add("Zensko");

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lables);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setSelection(spinnerAdapter.getPosition(doktor.getSpol()));

    }
}
