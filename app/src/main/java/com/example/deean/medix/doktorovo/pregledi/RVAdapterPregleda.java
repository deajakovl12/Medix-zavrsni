package com.example.deean.medix.doktorovo.pregledi;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.deean.medix.R;
import com.example.deean.medix.lijekovi.RecycleView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Deean on 27.6.2016..
 */
public class RVAdapterPregleda extends RecyclerView.Adapter<RVAdapterPregleda.PreglediViewHolder> {

    static Context context;
    List<Pregledi> pregledis;

    RVAdapterPregleda(Context context, List<Pregledi> pregledis){
        this.context  = context;
        this.pregledis = pregledis;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PreglediViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_card_view_pregleda,viewGroup,false);
        PreglediViewHolder pvh = new PreglediViewHolder(v);
        return  pvh;
    }

    @Override
    public void onBindViewHolder(PreglediViewHolder preglediViewHolder, int i) {
        preglediViewHolder.ime.setText(pregledis.get(i).getIme());
        preglediViewHolder.prezime.setText(pregledis.get(i).getPrezime());
        preglediViewHolder.oib.setText(pregledis.get(i).getOib());
        //preglediViewHolder.datum.setText(pregledis.get(i).getDatum().toString());
        //Calendar calendar = Calendar.getInstance();
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        preglediViewHolder.datum.setText(pregledis.get(i).getDatum_pregleda());
        preglediViewHolder.komentar.setText(pregledis.get(i).getKomentar());
    }

    @Override
    public int getItemCount() {
        return pregledis.size();
    }


    public static class PreglediViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        TextView ime, prezime, oib, datum,komentar;


        public PreglediViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            cv = (CardView) itemView.findViewById(R.id.cv);
            ime = (TextView) itemView.findViewById(R.id.pacijent_ime);
            prezime = (TextView) itemView.findViewById(R.id.pacijent_prezime);
            oib = (TextView) itemView.findViewById(R.id.OIB);
            datum = (TextView) itemView.findViewById(R.id.datum1);
            komentar = (TextView) itemView.findViewById(R.id.komentar);

        }

        @Override
        public void onClick(View v) {
            //ako bude trebalo kliknut na neki pregled
        }

    }
}
