package com.example.deean.medix.doktorovo.pregledi;

import android.text.format.DateFormat;

public class Pregledi {
    String ime, prezime, oib, komentar,datum_pregleda;


    public Pregledi(String ime, String prezime, String oib, String datum_pregleda, String komentar) {

        this.ime = ime;
        this.prezime = prezime;
        this.oib = oib;
        this.datum_pregleda = datum_pregleda;
        this.komentar = komentar;
    }
    public Pregledi(String ime, String prezime, String datum_pregleda, String komentar) {

        this.ime = ime;
        this.prezime = prezime;
        this.datum_pregleda = datum_pregleda;
        this.komentar = komentar;
    }

    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public String getOib() {
        return oib;
    }

    public String getDatum_pregleda() {
        return datum_pregleda;
    }

    public String getKomentar() {
        return komentar;
    }
}

