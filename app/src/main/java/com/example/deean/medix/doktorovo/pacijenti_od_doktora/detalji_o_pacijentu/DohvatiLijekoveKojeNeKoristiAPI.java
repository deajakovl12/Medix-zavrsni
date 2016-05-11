package com.example.deean.medix.doktorovo.pacijenti_od_doktora.detalji_o_pacijentu;

import com.example.deean.medix.lijekovi.Lijek;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Deean on 21.2.2016..
 */
public interface DohvatiLijekoveKojeNeKoristiAPI {
    String BASE_URL = "http://jaka12.heliohost.org";

    @GET("/dohvati_lijekove_koje_pacijent_ne_koristi.php")
    Call<ArrayList<Lijek>> response (@Query("pac_id") String pac_id);

    class Factory{
        private static DohvatiLijekoveKojeNeKoristiAPI service;
        public static DohvatiLijekoveKojeNeKoristiAPI getIstance(){
            if(service==null){
                Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL).build();
                service = retrofit.create(DohvatiLijekoveKojeNeKoristiAPI.class);
                return service;
            }
            else{
                return service;
            }
        }
    }
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .build();
    DohvatiLijekoveKojeNeKoristiAPI service = retrofit.create(DohvatiLijekoveKojeNeKoristiAPI.class);
}
