package com.example.deean.medix.doktorovo.pregledi;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.deean.medix.R;
import com.example.deean.medix.doktorovo.ToolbarActivity;
import com.example.deean.medix.doktorovo.konsturktor_i_baza.Doktor;
import com.example.deean.medix.doktorovo.konsturktor_i_baza.DoktorLokalno;
import com.example.deean.medix.doktorovo.pacijenti_od_doktora.PacijentAPI;
import com.example.deean.medix.doktorovo.pacijenti_od_doktora.detalji_o_pacijentu.SpremiPodatkeOZakazanomPregleduAPI;
import com.example.deean.medix.pacijentovo.konstruktor_i_baza.Pacijent;
import com.example.deean.medix.pocetni_zaslon.Login;
import com.github.ybq.android.spinkit.SpinKitView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DodajNoviPregled extends ToolbarActivity implements View.OnClickListener {
    Button bDatum, bVrijeme,bSpremi;
    SpinKitView skv;
    EditText etDatum, etVrijeme, etKomentar;
    private int mYear, mMonth, mDay, mHour, mMinute;

    Doktor doktor;
    DoktorLokalno doktorLokalno;

    private ArrayList<Pacijent> spremi;

    Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_novi_pregled);

        bDatum=(Button)findViewById(R.id.bDatum);
        bVrijeme=(Button)findViewById(R.id.bVrijeme);
        bSpremi = (Button) findViewById(R.id.bSpremi);
        etDatum=(EditText)findViewById(R.id.etDatum);
        etVrijeme=(EditText)findViewById(R.id.etVrijeme);
        etKomentar = (EditText) findViewById(R.id.etKomentar);
        spinner = (Spinner) findViewById(R.id.spinner);
        skv = (SpinKitView) findViewById(R.id.loading_view);

        bDatum.setOnClickListener(this);
        bVrijeme.setOnClickListener(this);
        bSpremi.setOnClickListener(this);

        doktorLokalno = new DoktorLokalno(this);
        doktor = doktorLokalno.getPrijavljenogDoktora();


        postaviDrawer(postaviToolbar("Dodaj novi termin pregleda"),doktor.getIme().toUpperCase(),doktor.getPrezime().toUpperCase(),doktor.getEmail(),doktor.getSpol()).build();
        dohvatiPacijente();
        //popuniSpinner();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bDatum:
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String dan = String.valueOf(dayOfMonth);
                                if (dayOfMonth < 10) {
                                    dan = "0" + dayOfMonth;
                                }
                                int mjesec_uvecan = monthOfYear + 1;
                                String mjesec = String.valueOf(mjesec_uvecan);
                                if (mjesec_uvecan < 10) {
                                    mjesec = "0" + mjesec_uvecan;
                                }
                                etDatum.setText(dan + "-" + mjesec + "-" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
            case R.id.bVrijeme:
                //popuniSpinner();
                final Calendar c1 = Calendar.getInstance();
                mHour = c1.get(Calendar.HOUR_OF_DAY);
                mMinute = c1.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                String sati = String.valueOf(hourOfDay);
                                if (hourOfDay < 10) {
                                    sati = "0" + hourOfDay;
                                }
                                String minuta = String.valueOf(minute);
                                if (minute < 10) {
                                    minuta = "0" + minute;
                                }
                                etVrijeme.setText(sati + ":" + minuta);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
                break;
            case R.id.bSpremi:
                spremi_podatke();
                //Log.e("KLIKNUTO","klikno spremi");
                break;

        }
    }

    private void spremi_podatke() {
        String dat = etDatum.getText().toString();
        String vrij = etVrijeme.getText().toString();
        String kom = etKomentar.getText().toString();
        String pac = spinner.getSelectedItem().toString();
        String[] oib = pac.split("-");
        String dat_vrij = dat + " " + vrij;
        //Log.e("Ispis podataka: ", dat + " " + vrij + " " + kom + " " + oib[1] + " " + dat_vrij);
        if(dat.equals("") || vrij.equals("") || kom.equals("")) {
            Toast.makeText(DodajNoviPregled.this, "Popunite sve podatke o pregledu!", Toast.LENGTH_SHORT).show();

        }
        else {
            bSpremi.setVisibility(View.GONE);
            skv.setVisibility(View.VISIBLE);
            SpremiPodatkeOZakazanomPregleduAPI.Factory.getIstance().response(oib[1],dat_vrij,kom,doktor.getId_doktor()).enqueue(new Callback<ArrayList<Integer>>() {
                @Override
                public void onResponse(Call<ArrayList<Integer>> call, Response<ArrayList<Integer>> response) {
                    bSpremi.setVisibility(View.VISIBLE);
                    skv.setVisibility(View.GONE);
                        Log.e("USPESNO", "SPREMLJENO");
                }

                @Override
                public void onFailure(Call<ArrayList<Integer>> call, Throwable t) {
                    bSpremi.setVisibility(View.VISIBLE);
                    skv.setVisibility(View.GONE);
                    AlertDialog.Builder dialogBuilder1 = new AlertDialog.Builder(DodajNoviPregled.this);
                    dialogBuilder1.setTitle("Uspješno!");
                    dialogBuilder1.setMessage("Dodali ste novi pregled!");
                    dialogBuilder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            startActivity(new Intent(DodajNoviPregled.this, PreglediOdDoktora.class));
                        }
                    });
                    dialogBuilder1.show();
                }
            });
        }

    }

    private void dohvatiPacijente() {
        spremi = new ArrayList<>();
        PacijentAPI.Factory.getIstance().response(doktor.getId_doktor()).enqueue(new Callback<ArrayList<Pacijent>>() {
            @Override
            public void onResponse(Call<ArrayList<Pacijent>> call, Response<ArrayList<Pacijent>> response) {
                for (int i = 0; i < response.body().size(); i++) {
                    spremi.add(new Pacijent(response.body().get(i).getIme(), response.body().get(i).getPrezime(), response.body().get(i).getOib()));
                    Log.e("PACIJENT: ", response.body().get(i).getIme());
                }
                popuniSpinner();

            }

            @Override
            public void onFailure(Call<ArrayList<Pacijent>> call, Throwable t) {
                Log.e("Failed", t.getMessage());
            }
        });
    }

        private void popuniSpinner() {
            List<String> lables = new ArrayList<String>();
            for (int i = 0; i < spremi.size(); i++) {
                lables.add(spremi.get(i).getIme().toUpperCase() + " " + spremi.get(i).getPrezime().toUpperCase() + "-" + spremi.get(i).getOib());
            }
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lables);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(spinnerAdapter);
        }
    }

