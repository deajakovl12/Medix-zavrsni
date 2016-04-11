package com.example.deean.medix.doktorovo.pacijenti_u_bazi;

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
import android.widget.TextView;

import com.example.deean.medix.doktorovo.konsturktor_i_baza.Doktor;
import com.example.deean.medix.doktorovo.konsturktor_i_baza.DoktorLokalno;
import com.example.deean.medix.pacijentovo.konstruktor_i_baza.Pacijent;
import com.example.deean.medix.R;
import com.example.deean.medix.pacijentovo.konstruktor_i_baza.PacijentLokalno;
import com.example.deean.medix.pocetni_zaslon.Login;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Deean on 21.2.2016..
 */
public class RVAdapterPacijenata  extends RecyclerView.Adapter<RVAdapterPacijenata.PacijentiViewHolder> {

    static Context context;

    public static class PacijentiViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cv;
        TextView pacijent_ime, pacijent_prezime, pacijent_adresa, pacijent_oib;
        String ime,prezime;

        private Doktor doktor;
        DoktorLokalno DoktorLokalno;

        ProgressDialog progressDialog;


        PacijentiViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            cv = (CardView) itemView.findViewById(R.id.cv);
            pacijent_ime = (TextView) itemView.findViewById(R.id.pacijent_ime);
            pacijent_prezime = (TextView) itemView.findViewById(R.id.pacijent_prezime);
            pacijent_adresa = (TextView) itemView.findViewById(R.id.pacijent_adresa);
            pacijent_oib = (TextView) itemView.findViewById(R.id.pacijent_oib);

            DoktorLokalno = new DoktorLokalno(context);
            doktor = DoktorLokalno.getPrijavljenogDoktora();

        }

        @Override
        public void onClick(View v) {
            ime=pacijent_ime.getText().toString();
            prezime=pacijent_prezime.getText().toString();
            Pozovi_detalje(ime, prezime);
        }

        public void Pozovi_detalje(String ime,String prezime) {
            final AlertDialog.Builder dialogBuilder1 = new AlertDialog.Builder(context);
            dialogBuilder1.setTitle("Potvrdi dodavanje pacijenta!");
            dialogBuilder1.setMessage("Jeste li sigurni da zelite dodati pacijenta: " + ime + " " + prezime + " u svoju listu pacijenata?");
            dialogBuilder1.setPositiveButton("DA", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    progressDialog = new ProgressDialog(context);
                    progressDialog.setCancelable(false);
                    progressDialog.setTitle("Obrađujem");
                    progressDialog.setMessage("Molim sačekajte...");
                    progressDialog.show();
                    PacijentuDajDoktoraAPI.Factory.getIstance().response(doktor.getId_doktor(),pacijent_oib.getText().toString()).enqueue(new Callback<ArrayList<Pacijent>>() {
                        @Override
                        public void onResponse(Call<ArrayList<Pacijent>> call, Response<ArrayList<Pacijent>> response) {
                            Log.e("Proslo", doktor.getId_doktor());
                        }
                        @Override
                        public void onFailure(Call<ArrayList<Pacijent>> call, Throwable t) {
                            Log.e("Nije proslo",doktor.getId_doktor());
                            progressDialog.dismiss();
                            AlertDialog.Builder dialogBuilder1 = new AlertDialog.Builder(context);
                            dialogBuilder1.setTitle("Uspješno!");
                            dialogBuilder1.setMessage("Dodali ste pacijenta: "+ pacijent_ime.getText().toString() + " " + pacijent_prezime.getText().toString() + " u svoje pacijente");
                            dialogBuilder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    context.startActivity(new Intent(context, RecycleViewPacijenata.class));
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
    List<Pacijent> pacijents;

    RVAdapterPacijenata(Context context, List<Pacijent> pacijents) {
        this.pacijents = pacijents;
        this.context = context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PacijentiViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_card_view_pacijenta, viewGroup, false);
        PacijentiViewHolder pvh = new PacijentiViewHolder(v);
        return pvh;
    }
    @Override
    public void onBindViewHolder(PacijentiViewHolder pacijentViewHolder, int i) {
        pacijentViewHolder.pacijent_ime.setText(pacijents.get(i).getIme());
        pacijentViewHolder.pacijent_prezime.setText(pacijents.get(i).getPrezime());
        pacijentViewHolder.pacijent_adresa.setText(pacijents.get(i).getAdresa());
        pacijentViewHolder.pacijent_oib.setText(pacijents.get(i).getOib());
    }
    @Override
    public int getItemCount() {
        return pacijents.size();
    }
}
