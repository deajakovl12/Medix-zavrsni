package com.example.deean.medix;

/**
 * Created by Deean on 21.2.2016..
 */
public class Lijek {
    String naziv, sastav, namjena, pakovanje, ime_i_adresa_proizvodaca, kada_ne_smije_primjeniti,doziranje,nuspojave;
    int photoId;

    Lijek(String naziv, int photoId) {
        this.naziv = naziv;
        this.photoId = photoId;
    }
    Lijek(String naziv, String sastav, String namjena, String pakovanje, String ime_i_adresa_proizvodaca, String kada_ne_smije_primjeniti, String doziranje, String nuspojave){
        this.naziv=naziv;
        this.sastav=sastav;
        this.namjena=namjena;
        this.pakovanje=pakovanje;
        this.ime_i_adresa_proizvodaca=ime_i_adresa_proizvodaca;
        this.kada_ne_smije_primjeniti=kada_ne_smije_primjeniti;
        this.doziranje=doziranje;
        this.nuspojave=nuspojave;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getSastav() {
        return sastav;
    }

    public void setSastav(String sastav) {
        this.sastav = sastav;
    }

    public String getNamjena() {
        return namjena;
    }

    public void setNamjena(String namjena) {
        this.namjena = namjena;
    }

    public String getPakovanje() {
        return pakovanje;
    }

    public void setPakovanje(String pakovanje) {
        this.pakovanje = pakovanje;
    }

    public String getIme_i_adresa_proizvodaca() {
        return ime_i_adresa_proizvodaca;
    }

    public void setIme_i_adresa_proizvodaca(String ime_i_adresa_proizvodaca) {
        this.ime_i_adresa_proizvodaca = ime_i_adresa_proizvodaca;
    }

    public String getKada_ne_smije_primjeniti() {
        return kada_ne_smije_primjeniti;
    }

    public void setKada_ne_smije_primjeniti(String kada_ne_smije_primjeniti) {
        this.kada_ne_smije_primjeniti = kada_ne_smije_primjeniti;
    }

    public String getDoziranje() {
        return doziranje;
    }

    public void setDoziranje(String doziranje) {
        this.doziranje = doziranje;
    }

    public String getNuspojave() {
        return nuspojave;
    }

    public void setNuspojave(String nuspojave) {
        this.nuspojave = nuspojave;
    }
}
