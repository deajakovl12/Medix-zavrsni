package com.example.deean.medix.doktorovo.pregledi;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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

import com.example.deean.medix.R;
import com.example.deean.medix.doktorovo.ToolbarActivity;
import com.example.deean.medix.doktorovo.konsturktor_i_baza.Doktor;
import com.example.deean.medix.doktorovo.konsturktor_i_baza.DoktorLokalno;
import com.example.deean.medix.doktorovo.pacijenti_od_doktora.PacijentAPI;
import com.example.deean.medix.pacijentovo.konstruktor_i_baza.Pacijent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DodajNoviPregled extends ToolbarActivity implements View.OnClickListener {
    Button bDatum, bVrijeme;
    EditText etDatum, etVrijeme;
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
        etDatum=(EditText)findViewById(R.id.etDatum);
        etVrijeme=(EditText)findViewById(R.id.etVrijeme);
        spinner = (Spinner) findViewById(R.id.spinner);

        bDatum.setOnClickListener(this);
        bVrijeme.setOnClickListener(this);

        doktorLokalno = new DoktorLokalno(this);
        doktor = doktorLokalno.getPrijavljenogDoktora();


        postaviDrawer(postaviToolbar("Dodaj novi termin pregleda"),doktor.getIme().toUpperCase(),doktor.getPrezime().toUpperCase(),doktor.getEmail()).build();
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

