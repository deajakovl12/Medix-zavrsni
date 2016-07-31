package com.example.deean.medix.pacijentovo.raspored_uzimanja_lijekova;

import android.app.AlarmManager;

/**
 * Created by Deean on 18.7.2016..
 */
public class AlarmKlasa {
    String unique_code, naziv,vrijeme_pocetka_uzimanja,uzimati_svakih,slika_lijeka;

    AlarmKlasa(String unique_code, String naziv, String vrijeme_pocetka_uzimanja, String uzimati_svakih, String slika_lijeka){
        this.unique_code = unique_code;
        this.naziv = naziv;
        this.vrijeme_pocetka_uzimanja = vrijeme_pocetka_uzimanja;
        this.uzimati_svakih = uzimati_svakih;
        this.slika_lijeka = slika_lijeka;
    }

    public String getUnique_code() {
        return unique_code;
    }

    public String getNaziv() {
        return naziv;
    }

    public String getVrijeme_pocetka_uzimanja() {
        return vrijeme_pocetka_uzimanja;
    }

    public String getUzimati_svakih() {
        return uzimati_svakih;
    }

    public String getSlika_lijeka() {
        return slika_lijeka;
    }
}
