package com.example.deean.medix.doktorovo.konsturktor_i_baza;

/**
 * Created by Deean on 10.2.2016..
 */
public class Doktor {
    String id_doktor;
    String ime;
    String prezime;
    String adresa;
    String lozinka;
    String telefon;
    String oib;
    String email;
    String radno_vrijeme;
    String rad_savjetovalista;
    String mobitel;
    String titula;
    String spol;



    public Doktor(String ime, String prezime, String adresa,String oib, String lozinka, String telefon,  String email, String radno_vrijeme, String rad_savjetovalista, String mobitel,String titula){

        this.ime=ime;
        this.prezime=prezime;
        this.adresa=adresa;
        this.lozinka=lozinka;
        this.telefon=telefon;
        this.oib=oib;
        this.email=email;
        this.radno_vrijeme=radno_vrijeme;
        this.rad_savjetovalista=rad_savjetovalista;
        this.mobitel=mobitel;
        this.titula=titula;
    }
    public Doktor(String ime, String prezime, String titula,String radno_vrijeme,String rad_savjetovalista, String adresa, String telefon, String mobitel, String spol){

        this.ime=ime;
        this.prezime=prezime;
        this.titula=titula;
        this.radno_vrijeme=radno_vrijeme;
        this.rad_savjetovalista=rad_savjetovalista;
        this.adresa=adresa;
        this.telefon=telefon;
        this.mobitel=mobitel;
        this.spol = spol;

    }

    public Doktor(String email, String lozinka){
        this.email=email;
        this.lozinka=lozinka;
        this.ime="";
        this.prezime="";
        this.adresa="";
        this.telefon="";
        this.oib="";
        this.radno_vrijeme="";
        this.rad_savjetovalista="";
        this.mobitel="";
        this.titula="";
    }
    public Doktor(String email){
        this.email=email;
        this.lozinka="";
        this.ime="";
        this.prezime="";
        this.adresa="";
        this.telefon="";
        this.oib="";
        this.radno_vrijeme="";
        this.rad_savjetovalista="";
        this.mobitel="";
        this.titula="";
    }

    public Doktor(String id_doktor,String ime, String prezime, String adresa,String oib, String lozinka, String telefon,  String email, String radno_vrijeme, String rad_savjetovalista, String mobitel,String titula,String spol){
        this.id_doktor=id_doktor;
        this.ime=ime;
        this.prezime=prezime;
        this.adresa=adresa;
        this.lozinka=lozinka;
        this.telefon=telefon;
        this.oib=oib;
        this.email=email;
        this.radno_vrijeme=radno_vrijeme;
        this.rad_savjetovalista=rad_savjetovalista;
        this.mobitel=mobitel;
        this.titula=titula;
        this.spol = spol;
    }

    public String getSpol() {
        return spol;
    }

    public void setSpol(String spol) {
        this.spol = spol;
    }


    public String getId_doktor() {
        return id_doktor;
    }

    public void setId_doktor(String id_doktor) {
        this.id_doktor = id_doktor;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getOib() {
        return oib;
    }

    public void setOib(String oib) {
        this.oib = oib;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRadno_vrijeme() {
        return radno_vrijeme;
    }

    public void setRadno_vrijeme(String radno_vrijeme) {
        this.radno_vrijeme = radno_vrijeme;
    }

    public String getRad_savjetovalista() {
        return rad_savjetovalista;
    }

    public void setRad_savjetovalista(String rad_savjetovalista) {
        this.rad_savjetovalista = rad_savjetovalista;
    }

    public String getMobitel() {
        return mobitel;
    }

    public void setMobitel(String mobitel) {
        this.mobitel = mobitel;
    }

    public String getTitula() {
        return titula;
    }

    public void setTitula(String titula) {
        this.titula = titula;
    }
}

