package com.example.deean.medix.pacijentovo.raspored_uzimanja_lijekova;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.deean.medix.R;
import com.example.deean.medix.databinding.ActivityRasporedUzimanjaLijekovaBinding;
import com.example.deean.medix.doktorovo.ToolbarActivity;
import com.example.deean.medix.pacijentovo.konstruktor_i_baza.Pacijent;
import com.example.deean.medix.pacijentovo.konstruktor_i_baza.PacijentLokalno;

import java.util.Calendar;

public class RasporedUzimanjaLijekova extends ToolbarActivity {


    Pacijent pacijent;
    PacijentLokalno pacijentLokalno;
    ActivityRasporedUzimanjaLijekovaBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this,R.layout.activity_raspored_uzimanja_lijekova);
        pacijentLokalno = new PacijentLokalno(this);
        pacijent = pacijentLokalno.getPrijavljenogPacijenta();
        postaviDrawer(postaviToolbar("Raspored uzimanja lijekova"), pacijent.getIme(), pacijent.getPrezime(), pacijent.getEmail()).build();

        bind.bPretraga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("CLICKED","Clicked");

                Calendar calendar = Calendar.getInstance();

                calendar.set(Calendar.HOUR_OF_DAY,15);
                calendar.set(Calendar.MINUTE,56);
                calendar.set(Calendar.SECOND,20);

                Calendar c2 = Calendar.getInstance();

                c2.set(Calendar.HOUR_OF_DAY,15);
                c2.set(Calendar.MINUTE,57);
                c2.set(Calendar.SECOND,55);

                AlarmKlasa alarmKlasa = new AlarmKlasa(100,"Uzeti lijek pivo");

                AlarmKlasa alarmKlasa2 = new AlarmKlasa(200,"Uzeti lijek vino");


                //TODO OSTAVITI ALARMEICI NAPRAVITI ACTIVITY GDJE SE SPREMA SVE VEZANO UZ DODAVANJE EVIDENCIJE O PIJENJU LIJEKA

                Intent intent = new Intent(getApplicationContext(),NotificationReceiver.class);

                intent.putExtra("kljuc", "100");

                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),100,intent,0); //PendingIntent.FLAG_UPDATE_CURRENT)

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),1000*60*1,pendingIntent);


                Intent cancellationIntent = new Intent(getApplicationContext(), NotificationReceiver.class);
                //cancellationIntent.putExtra("key", pendingIntent);
                cancellationIntent.putExtra("kljuc", "200");

                PendingIntent cancellationPendingIntent = PendingIntent.getBroadcast(getApplicationContext(),200, cancellationIntent, 0);//PendingIntent.FLAG_UPDATE_CURRENT
                AlarmManager alarmManager2 = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager2.setRepeating(AlarmManager.RTC_WAKEUP, c2.getTimeInMillis(),1000*60*3, cancellationPendingIntent);
            }
        });


    bind.ivDodaj.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(),DodajNoviLijekZaUzimanje.class));
        }
    });
    }
}
