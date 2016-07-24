package com.example.deean.medix.pacijentovo.raspored_uzimanja_lijekova;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
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
import com.example.deean.medix.databinding.ActivityDodajNoviLijekZaUzimanjeBinding;
import com.example.deean.medix.doktorovo.ToolbarActivity;
import com.example.deean.medix.doktorovo.pacijenti_od_doktora.PacijentAPI;
import com.example.deean.medix.doktorovo.pregledi.PreglediOdDoktora;
import com.example.deean.medix.lijekovi.Lijek;
import com.example.deean.medix.lijekovi.LijekAPI;
import com.example.deean.medix.pacijentovo.konstruktor_i_baza.Pacijent;
import com.example.deean.medix.pacijentovo.konstruktor_i_baza.PacijentLokalno;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DodajNoviLijekZaUzimanje extends ToolbarActivity implements View.OnClickListener {

    //ActivityDodajNoviLijekZaUzimanjeBinding bind;

    EditText etDatum, etVrijeme, etVrijemeUzimanja;
    Spinner spinner;

    Button bDatum, bVrijeme, bSpremiLijek;

    private Pacijent pacijent;
    private PacijentLokalno pacijentLokalno;

    private ArrayList<Lijek> spremi;

    private int mYear, mMonth, mDay, mHour, mMinute;

    Random rand = new Random();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_novi_lijek_za_uzimanje);
        pacijentLokalno = new PacijentLokalno(this);
        pacijent = pacijentLokalno.getPrijavljenogPacijenta();
        postaviDrawer(postaviToolbar("Dodaj novi lijek za korištenje"), pacijent.getIme(), pacijent.getPrezime(), pacijent.getEmail()).build();

        spinner = (Spinner) findViewById(R.id.spinner);
        etDatum = (EditText) findViewById(R.id.etDatum);
        etVrijeme = (EditText) findViewById(R.id.etVrijeme);
        etVrijemeUzimanja = (EditText) findViewById(R.id.etVrijemeUzimanja);
        bDatum = (Button) findViewById(R.id.bDatum);
        bVrijeme = (Button) findViewById(R.id.bVrijeme);
        bSpremiLijek = (Button) findViewById(R.id.bSpremiTermin);


        bDatum.setOnClickListener(this);
        bVrijeme.setOnClickListener(this);
        bSpremiLijek.setOnClickListener(this);
        dohvatiLijekove();
    }


    private void dohvatiLijekove() {
        spremi = new ArrayList<>();
        LijekAPI.Factory.getIstance().response().enqueue(new Callback<ArrayList<Lijek>>() {
            @Override
            public void onResponse(Call<ArrayList<Lijek>> call, Response<ArrayList<Lijek>> response) {
                for (int i = 0; i < response.body().size(); i++) {
                    spremi.add(new Lijek(response.body().get(i).getNaziv(),response.body().get(i).getSlika_lijeka()));
                }
                popuniSpinner(spinner);

            }
            @Override
            public void onFailure(Call<ArrayList<Lijek>> call, Throwable t) {
                Log.e("Failed", t.getMessage());
            }
        });
    }

    private void popuniSpinner(Spinner spinner) {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < spremi.size(); i++) {
            lables.add(spremi.get(i).getNaziv().toUpperCase());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lables);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
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
            case R.id.bSpremiTermin:
                kreirajAlarm();

                break;
        }
    }

    private void kreirajAlarm(){
        String nazivLijeka = spinner.getSelectedItem().toString();
        String datumPocetkaIVrijeme = etDatum.getText().toString() + " " +  etVrijeme.getText().toString();
        String invervalUzimanjaLijeka = etVrijemeUzimanja.getText().toString();

        if(nazivLijeka.equals("") || etVrijeme.getText().toString().equals("") || etDatum.getText().toString().equals("") || invervalUzimanjaLijeka.equals("")){
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DodajNoviLijekZaUzimanje.this);
            dialogBuilder.setMessage("Popunite sve podatke!");
            dialogBuilder.setPositiveButton("Ok",null);
            dialogBuilder.show();
        }
        else{
            Calendar calendar = Calendar.getInstance();

            Calendar calendar2 = Calendar.getInstance();

            String[] danMjesecGodina = etDatum.getText().toString().split("-");
            String[] satMinuta = etVrijeme.getText().toString().split(":");


            //TODO NAPRAVITI KOLIKO VREMENSKI PRIJE SE TREBA JAVITI ALARM NPR 10 MIN PRIJE UZIMANJA LIJEKA I SLICNO
            calendar.set(Calendar.YEAR,mYear);
            calendar.set(Calendar.MONTH,mMonth);
            calendar.set(Calendar.DAY_OF_MONTH,mDay);
            calendar.set(Calendar.HOUR_OF_DAY,mHour);
            calendar.set(Calendar.MINUTE,mMinute);
            calendar.set(Calendar.SECOND,20);

            calendar2.set(Integer.parseInt(danMjesecGodina[2]), Integer.parseInt(danMjesecGodina[1])-1, Integer.parseInt(danMjesecGodina[0]), Integer.parseInt(satMinuta[0]), Integer.parseInt(satMinuta[1]));

            int randomNum = rand.nextInt((9999999 - 1) + 1) + 1;

            Log.e("RANDOM NUM", String.valueOf(randomNum)+ " da");
            Log.e("Kalendar njihov", String.valueOf(calendar) + " da");
            Log.e("Kalendar njihov2", String.valueOf(calendar2) + " da");
            Log.e("Kalendar moj", datumPocetkaIVrijeme +  " da");

            Intent intent = new Intent(getApplicationContext(),NotificationReceiver.class);

            intent.putExtra("kljuc", String.valueOf(randomNum));
            intent.putExtra("imeLijeka", nazivLijeka);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),randomNum,intent,0); //PendingIntent.FLAG_UPDATE_CURRENT)

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar2.getTimeInMillis(),1000*60*Integer.parseInt(invervalUzimanjaLijeka),pendingIntent);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar2.getTimeInMillis(),1000*60*Integer.parseInt(invervalUzimanjaLijeka),pendingIntent);
            //alarmManager.se

            AlertDialog.Builder dialogBuilder1 = new AlertDialog.Builder(DodajNoviLijekZaUzimanje.this);
            dialogBuilder1.setTitle("Uspješno!");
            dialogBuilder1.setMessage("Dodali ste novi termin uzimanja lijeka!");
            dialogBuilder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    startActivity(new Intent(DodajNoviLijekZaUzimanje.this,RasporedUzimanjaLijekova.class));
                }
            });
            dialogBuilder1.show();
        }
    }
}
