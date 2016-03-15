package com.example.deean.medix;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Deean on 21.2.2016..
 */
public class RVAdapterPacijenta  extends RecyclerView.Adapter<RVAdapterPacijenta.PacijentViewHolder> {

    static Context context;

    public static class PacijentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cv;
        TextView pacijent_ime, pacijent_prezime, pacijent_adresa, pacijent_oib;
        String oib;

        PacijentViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            cv = (CardView) itemView.findViewById(R.id.cv);
            pacijent_ime = (TextView) itemView.findViewById(R.id.pacijent_ime);
            pacijent_prezime = (TextView) itemView.findViewById(R.id.pacijent_prezime);
            pacijent_adresa = (TextView) itemView.findViewById(R.id.pacijent_adresa);
            pacijent_oib = (TextView) itemView.findViewById(R.id.pacijent_oib);
        }

        @Override
        public void onClick(View v) {
            oib=pacijent_oib.getText().toString();
            Pozovi_detalje(oib);
        }

        public void Pozovi_detalje(String ime){
            Intent intent1 = new Intent(context, FragmentPacijentDetalji.class);
            intent1.putExtra("ime1",ime);
            context.startActivity(intent1);
        }

        }

        List<Pacijent> pacijents;

        RVAdapterPacijenta(Context context, List<Pacijent> pacijents) {
            this.pacijents = pacijents;
            this.context = context;
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }

        @Override
        public PacijentViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_card_view_pacijenta, viewGroup, false);
            PacijentViewHolder pvh = new PacijentViewHolder(v);
            return pvh;
        }

        @Override
        public void onBindViewHolder(PacijentViewHolder pacijentViewHolder, int i) {
            pacijentViewHolder.pacijent_ime.setText(pacijents.get(i).ime);
            pacijentViewHolder.pacijent_prezime.setText(pacijents.get(i).prezime);
            pacijentViewHolder.pacijent_adresa.setText(pacijents.get(i).adresa);
            pacijentViewHolder.pacijent_oib.setText(pacijents.get(i).oib);
        }

        @Override
        public int getItemCount() {
            return pacijents.size();
        }
}
