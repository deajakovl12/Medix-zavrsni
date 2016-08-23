package com.example.deean.medix.pacijentovo.raspored_uzimanja_lijekova;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deean.medix.R;
import com.example.deean.medix.lijekovi.RecycleView;
import com.example.deean.medix.pacijentovo.raspored_uzimanja_lijekova.AlarmKlasa;
import com.example.deean.medix.pocetni_zaslon.Login;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Deean on 27.6.2016..
 */
public class RVAadapterAlarma extends RecyclerView.Adapter<RVAadapterAlarma.AlarmiViewHolder> {

    static Context context;
    List<AlarmKlasa> alarms;

    RVAadapterAlarma(Context context, List<AlarmKlasa> alarms){
        this.context  = context;
        this.alarms = alarms;
    }

    private void refresh(){
        notifyDataSetChanged();
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public AlarmiViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_card_view_alarma,viewGroup,false);
        AlarmiViewHolder avh = new AlarmiViewHolder(v);
        return  avh;
    }

    @Override
    public void onBindViewHolder(AlarmiViewHolder alarmiViewHolder, int i) {
        alarmiViewHolder.naziv.setText(alarms.get(i).getNaziv());
        alarmiViewHolder.vrijeme_pocetka.setText(alarms.get(i).getVrijeme_pocetka_uzimanja());
        alarmiViewHolder.reqCode.setText(alarms.get(i).getUnique_code());
        alarmiViewHolder.uzimati_svakih.setText("Svakih " + alarms.get(i).getUzimati_svakih() + " sata");
        Picasso.with(context).load(alarms.get(i).getSlika_lijeka()).into(alarmiViewHolder.slika);
    }
    @Override
    public int getItemCount() {
        return alarms.size();
    }


    public class AlarmiViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        TextView naziv, vrijeme_pocetka, uzimati_svakih,reqCode;
        ImageView slika;


        public AlarmiViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            cv = (CardView) itemView.findViewById(R.id.cv);
            naziv = (TextView) itemView.findViewById(R.id.lijek_ime);
            vrijeme_pocetka = (TextView) itemView.findViewById(R.id.vrijeme_pocetka);
            uzimati_svakih = (TextView) itemView.findViewById(R.id.uzimati_svakih);
            slika = (ImageView) itemView.findViewById(R.id.lijek_slika);
            reqCode = (TextView) itemView.findViewById(R.id.req_code);

        }

        @Override
        public void onClick(View v) {

            Log.e("REQCODE", reqCode.getText().toString());
            AlertDialog.Builder dialogBuilder1 = new AlertDialog.Builder(context);
            dialogBuilder1.setTitle("Jeste li sigurni?");
            dialogBuilder1.setMessage("Želite li prekinut dolazak notifikacija za lijek:\n" + naziv.getText().toString() + " ?");
            dialogBuilder1.setPositiveButton("Da", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent = new Intent(context,NotificationReceiver.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context,Integer.parseInt(reqCode.getText().toString()),intent,0);
                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
                    alarmManager.cancel(pendingIntent);
                    PonistiAlarmAPI.Factory.getIstance().response(reqCode.getText().toString()).enqueue(new Callback<ArrayList<Integer>>() {
                        @Override
                        public void onResponse(Call<ArrayList<Integer>> call, Response<ArrayList<Integer>> response) {
                        }
                        @Override
                        public void onFailure(Call<ArrayList<Integer>> call, Throwable t) {
                        }
                    });
                    context.startActivity(new Intent(context, RasporedUzimanjaLijekova.class));
                }
            });
            dialogBuilder1.setNegativeButton("Ne", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            dialogBuilder1.show();



        }



    }
}
