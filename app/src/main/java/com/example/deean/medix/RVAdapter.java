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
public class RVAdapter  extends RecyclerView.Adapter<RVAdapter.LijekViewHolder> {
    static Context context;



    public static class LijekViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cv;
        TextView lijek_ime;
        ImageView lijek_slika;
        String ime;

        LijekViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            cv = (CardView)itemView.findViewById(R.id.cv);
            lijek_ime = (TextView)itemView.findViewById(R.id.lijek_ime);
            lijek_slika = (ImageView)itemView.findViewById(R.id.lijek_slika);
        }

        @Override
        public void onClick(View v) {
            ime=lijek_ime.getText().toString();
            switch (ime) {
                case "Naklofen":
                    Pozovi_detalje(ime);
                    break;
                case "Lupocet 500mg tablete":
                    Pozovi_detalje(ime);
                    break;
                case "Neofen":
                    Pozovi_detalje(ime);
                    break;
            }
        }
        public void Pozovi_detalje(String ime){
            Intent intent1 = new Intent(context, FragmentLijekDetalji.class);
            intent1.putExtra("ime1",ime);
            context.startActivity(intent1);
        }
    }

    List<Lijek> lijeks;

    RVAdapter(Context context, List<Lijek> lijeks){
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
        lijekViewHolder.lijek_ime.setText(lijeks.get(i).naziv);
        lijekViewHolder.lijek_slika.setImageResource(lijeks.get(i).photoId);
    }

    @Override
    public int getItemCount() {
        return lijeks.size();
    }
}
