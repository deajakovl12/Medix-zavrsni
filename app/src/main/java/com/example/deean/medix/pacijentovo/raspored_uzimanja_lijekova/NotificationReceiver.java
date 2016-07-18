package com.example.deean.medix.pacijentovo.raspored_uzimanja_lijekova;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.deean.medix.R;
import com.example.deean.medix.pacijentovo.Prijava_Pacijent;

/**
 * Created by Deean on 17.7.2016..
 */
public class NotificationReceiver extends BroadcastReceiver {
    private static int brojac = 0;
    @Override
    public void onReceive(Context context, Intent intent) {

            //AlarmKlasa alarmic = Parcels.unwrap(intent.getParcelableExtra("Kljuc"));

            //Log.e("KLJUC: ",alarmic.getKod() + " " +  alarmic.getTekst());

            String rkod = intent.getStringExtra("kljuc");

            String nazivLijkea = intent.getStringExtra("imeLijeka");

            Log.e("STRING RKOD",rkod + " KOd");
            Log.e("STRING LIJEK",nazivLijkea + " KOd");



        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            Intent repeatingIntent = new Intent(context, RasporedUzimanjaLijekova.class);

            repeatingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(context, Integer.parseInt(rkod), repeatingIntent, 0); //PendingIntent.FLAG_UPDATE_CURRENT


            Uri sound = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.alarmsound);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.small_logo_white)
                    .setContentTitle("Obavijest o uzimanju lijeka")
                    .setContentText("Uzmite lijek " + nazivLijkea)
                    .setSound(sound)
                    .setAutoCancel(true);

            brojac++;
            Log.e("BROJAC", String.valueOf(brojac));
            notificationManager.notify(Integer.parseInt(rkod), builder.build());
    }
}
