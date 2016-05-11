package com.example.deean.medix.lijekovi;

import android.content.Context;
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
import com.example.deean.medix.doktorovo.pacijenti_od_doktora.detalji_o_pacijentu.DodajLijekPacijentu;
import com.example.deean.medix.lijekovi.detalji_o_lijeku.FragmentLijekDetalji;

import java.util.List;

/**
 * Created by Deean on 21.2.2016..
 */
public class RVAdapter  extends RecyclerView.Adapter<RVAdapter.LijekViewHolder> {
    static Context context;



    public static class LijekViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cv;
        TextView lijek_ime;
        ImageView lijek_slika;
        String ime;
        String dohvati_ime_klase;

        DodajLijekPacijentu dlp = new DodajLijekPacijentu();

        LijekViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            cv = (CardView)itemView.findViewById(R.id.cv);
            lijek_ime = (TextView)itemView.findViewById(R.id.lijek_ime);
            lijek_slika = (ImageView)itemView.findViewById(R.id.lijek_slika);
        }

        @Override
        public void onClick(View v) {
            /*dohvati_ime_klase = dlp.getImeKlase();
            Log.e("Ime klase u rvaadapter",dlp.getImeKlase()+"  bla");
            if(dohvati_ime_klase.equals("DodajLijekPacijentu")){
                Log.e("Ime klase","dodaj lijek pacijentu");
            }*/
            //else{
                ime=lijek_ime.getText().toString();
                Pozovi_detalje(ime);
                Log.e("Ime klase",this.getClass().getSimpleName().toString());
            //}

        }
        public void Pozovi_detalje(String ime){
            Intent intent1 = new Intent(context, FragmentLijekDetalji.class);
            intent1.putExtra("ime1",ime);
            context.startActivity(intent1);
        }
    }

    List<Lijek> lijeks;

    public RVAdapter(Context context, List<Lijek> lijeks){
        this.lijeks = lijeks;
        this.context=context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public LijekViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_card_view_lijek, viewGroup, false);
        LijekViewHolder lvh = new LijekViewHolder(v);
        return lvh;
    }

    @Override
    public void onBindViewHolder(LijekViewHolder lijekViewHolder, int i) {
        lijekViewHolder.lijek_ime.setText(lijeks.get(i).getNaziv());
        lijekViewHolder.lijek_slika.setImageResource(lijeks.get(i).getPhotoId());
    }

    @Override
    public int getItemCount() {
        return lijeks.size();
    }
}
