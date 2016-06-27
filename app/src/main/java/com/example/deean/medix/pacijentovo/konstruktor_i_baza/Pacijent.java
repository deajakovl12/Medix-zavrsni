package com.example.deean.medix.pacijentovo.konstruktor_i_baza;

/**
 * Created by Deean on 10.2.2016..
 */
public class Pacijent {
    String ime, prezime, adresa, lozinka, telefon, oib,email,spol,mobitel,bolesti,laboratorijski, id_pacijent;

    public Pacijent(String ime, String prezime, String adresa,String oib, String lozinka, String telefon,  String email, String spol,String mobitel){

        this.ime=ime;
        this.prezime=prezime;
        this.adresa=adresa;
        this.lozinka=lozinka;
        this.telefon=telefon;
        this.oib=oib;
        this.email=email;
        this.spol=spol;
        this.mobitel=mobitel;
    }

    public Pacijent(String email, String lozinka){
        this.email=email;
        this.lozinka=lozinka;
        this.ime="";
        this.prezime="";
        this.adresa="";
        this.telefon="";
        this.oib="";
        this.spol="";
        this.mobitel="";
    }
    public Pacijent(String email){
        this.email=email;
        this.lozinka="";
        this.ime="";
        this.prezime="";
        this.adresa="";
        this.telefon="";
        this.oib="";
        this.spol="";
        this.mobitel="";
    }

    public Pacijent(String ime, String prezime, String adresa, String oib){
        this.ime=ime;
        this.prezime=prezime;
        this.adresa=adresa;
        this.oib=oib;
    }
    public Pacijent(String ime, String prezime,String oib){
        this.ime=ime;
        this.prezime=prezime;
        this.oib=oib;
    }

    public Pacijent(String ime, String prezime, String adresa,String oib, String lozinka, String telefon,  String email, String spol,String mobitel, String bolesti, String laboratorijski){

        this.ime=ime;
        this.prezime=prezime;
        this.adresa=adresa;
        this.lozinka=lozinka;
        this.telefon=telefon;
        this.oib=oib;
        this.email=email;
        this.spol=spol;
        this.mobitel=mobitel;
        this.bolesti=bolesti;
        this.laboratorijski=laboratorijski;
    }

    public Pacijent(String id_pacijent,String ime, String prezime, String adresa,String oib, String lozinka, String telefon,  String email, String spol,String mobitel, String bolesti, String laboratorijski){
        this.id_pacijent=id_pacijent;
        this.ime=ime;
        this.prezime=prezime;
        this.adresa=adresa;
        this.lozinka=lozinka;
        this.telefon=telefon;
        this.oib=oib;
        this.email=email;
        this.spol=spol;
        this.mobitel=mobitel;
        this.bolesti=bolesti;
        this.laboratorijski=laboratorijski;
    }

    public String getId_pacijent() {
        return id_pacijent;
    }

    public void setId_pacijent(String id_pacijent) {
        this.id_pacijent = id_pacijent;
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

    public String getOib() {
        return oib;
    }

    public void setOib(String oib) {
        this.oib = oib;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSpol() {
        return spol;
    }

    public void setSpol(String spol) {
        this.spol = spol;
    }

    public String getMobitel() {
        return mobitel;
    }

    public void setMobitel(String mobitel) {
        this.mobitel = mobitel;
    }

    public String getBolesti() {
        return bolesti;
    }

    public void setBolesti(String bolesti) {
        this.bolesti = bolesti;
    }

    public String getLaboratorijski() {
        return laboratorijski;
    }

    public void setLaboratorijski(String laboratorijski) {
        this.laboratorijski = laboratorijski;
    }
}

