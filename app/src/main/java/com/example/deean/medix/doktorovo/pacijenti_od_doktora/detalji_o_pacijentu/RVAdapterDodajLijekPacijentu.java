package com.example.deean.medix.doktorovo.pacijenti_od_doktora.detalji_o_pacijentu;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import com.example.deean.medix.doktorovo.pacijenti_od_doktora.detalji_o_pacijentu.DodajLijekPacijentu;
import com.example.deean.medix.doktorovo.pacijenti_u_bazi.PacijentuDajDoktoraAPI;
import com.example.deean.medix.doktorovo.pacijenti_u_bazi.RecycleViewPacijenata;
import com.example.deean.medix.lijekovi.Lijek;
import com.example.deean.medix.lijekovi.detalji_o_lijeku.FragmentLijekDetalji;
import com.example.deean.medix.pacijentovo.konstruktor_i_baza.Pacijent;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Deean on 21.2.2016..
 */
public class RVAdapterDodajLijekPacijentu  extends RecyclerView.Adapter<RVAdapterDodajLijekPacijentu.LijekViewHolder> {
    static Context context;
    public static class LijekViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cv;
        TextView lijek_ime;
        ImageView lijek_slika;
        String ime;
        ProgressDialog progressDialog;


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
            Pozovi_detalje_2();
             Log.e("Ime klase",lijek_ime.getText().toString());
        }
        public void Pozovi_detalje_2() {
            Log.e("Ime funkcije","pozovi detalje 2");
            AlertDialog.Builder dialogBuilder1 = new AlertDialog.Builder(context);
            dialogBuilder1.setTitle("Potvrdi dodavanje lijeka!");
            dialogBuilder1.setMessage("Jeste li sigurni da želite dodati lijek " + lijek_ime.getText().toString() + " pacijentu?");
            dialogBuilder1.setPositiveButton("DA", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    progressDialog = new ProgressDialog(context);
                    progressDialog.setCancelable(false);
                    progressDialog.setTitle("Obrađujem");
                    progressDialog.setMessage("Molim sačekajte...");
                    progressDialog.show();
                    PacijentuDajLijekAPI.Factory.getIstance().response(lijek_ime.getText().toString(),OsobniPodaciFragment.id_pacijenta).enqueue(new Callback<ArrayList<Boolean>>() {
                        @Override
                        public void onResponse(Call<ArrayList<Boolean>> call, Response<ArrayList<Boolean>> response) {
                            Log.e("Proslo", "proslo je");
                        }
                        @Override
                        public void onFailure(Call<ArrayList<Boolean>> call, Throwable t) {
                            Log.e("Nije proslo","nije proslo");
                            progressDialog.dismiss();
                            AlertDialog.Builder dialogBuilder1 = new AlertDialog.Builder(context);
                            dialogBuilder1.setTitle("Uspješno!");
                            dialogBuilder1.setMessage("Dodali ste lijek: "+ lijek_ime.getText().toString() + " pacijentu!");
                            dialogBuilder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    context.startActivity(new Intent(context, DodajLijekPacijentu.class));
                                }
                            });
                            dialogBuilder1.show();
                        }
                    });
                }
            });
            dialogBuilder1.setNegativeButton("NE", null);
            dialogBuilder1.show();
        }
    }

    List<Lijek> lijeks;

    public RVAdapterDodajLijekPacijentu(Context context, List<Lijek> lijeks){
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
