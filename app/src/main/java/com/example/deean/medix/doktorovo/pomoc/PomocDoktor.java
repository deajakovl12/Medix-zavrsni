package com.example.deean.medix.doktorovo.pomoc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.deean.medix.R;
import com.example.deean.medix.doktorovo.ToolbarActivity;
import com.example.deean.medix.doktorovo.konsturktor_i_baza.Doktor;
import com.example.deean.medix.doktorovo.konsturktor_i_baza.DoktorLokalno;

public class PomocDoktor extends ToolbarActivity {
    TextView tvPlus,tvPostavke,tvPregledi,tvLozinka;
    Doktor doktor;
    DoktorLokalno doktorLokalno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomoc_doktor);

        tvPlus = (TextView) findViewById(R.id.tvPlus);
        tvPostavke = (TextView) findViewById(R.id.tvPostavke);
        tvPregledi = (TextView) findViewById(R.id.tvPregled);
        tvLozinka = (TextView) findViewById(R.id.tvLozinka);


        tvPlus.setText("Znak '+' znaći dodavanje npr. novog pacijenta, novog termina pregleda, dodavanje lijekova pacijentu i slično.");
        tvPostavke.setText("Postavke profila su postavke koje će biti prikazane za određenog doktora, doktor u bilo kojem trenutku može promijeniti postavke." +
                "Npr. može mjenjati radno vrijeme, vrijeme rada savjetovališta, promjeniti broj telefon, mobitela i slično.");
        tvPregledi.setText("Preglede doktor može filtrirati na način da u pretraživanje upiše npr. određeni datum, te će mu onda biti prikazani svi pregledi koje ima zakazane za taj dan koji je upisan.");
        tvLozinka.setText("Ako je lozinka kojim slučajem zaboravljena, doktor ju može resetirati tako da ode na zaboravljenu lozinku, te upiše e-mail adresu.");

        doktorLokalno = new DoktorLokalno(this);
        doktor = doktorLokalno.getPrijavljenogDoktora();

        postaviDrawer(postaviToolbar("Pomoć"),doktor.getIme().toUpperCase(),doktor.getPrezime().toUpperCase(),doktor.getEmail()).build();

    }
}
