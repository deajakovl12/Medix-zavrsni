package com.example.deean.medix.pacijentovo.raspored_uzimanja_lijekova;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deean.medix.R;
import com.example.deean.medix.lijekovi.RecycleView;
import com.example.deean.medix.pacijentovo.raspored_uzimanja_lijekova.AlarmKlasa;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

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
        alarmiViewHolder.uzimati_svakih.setText("Svakih " + alarms.get(i).getUzimati_svakih() + " sata");
        Picasso.with(context).load(alarms.get(i).getSlika_lijeka()).into(alarmiViewHolder.slika);
    }
    @Override
    public int getItemCount() {
        return alarms.size();
    }


    public static class AlarmiViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        TextView naziv, vrijeme_pocetka, uzimati_svakih;
        ImageView slika;


        public AlarmiViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            cv = (CardView) itemView.findViewById(R.id.cv);
            naziv = (TextView) itemView.findViewById(R.id.lijek_ime);
            vrijeme_pocetka = (TextView) itemView.findViewById(R.id.vrijeme_pocetka);
            uzimati_svakih = (TextView) itemView.findViewById(R.id.uzimati_svakih);
            slika = (ImageView) itemView.findViewById(R.id.lijek_slika);

        }

        @Override
        public void onClick(View v) {
            //ako bude trebalo kliknut na neki pregled
            //TODO KAD SE KLIKNE NA RASPORED UZIMANJA ONDA OBRISATI TAJ ALARM!!
        }

    }
}
