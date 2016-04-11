package com.example.deean.medix.doktorovo.pacijenti_u_bazi;


import com.example.deean.medix.pacijentovo.konstruktor_i_baza.Pacijent;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by Deean on 21.2.2016..
 */
public interface PacijenteDohvatiAPI {
    String BASE_URL = "http://jaka12.heliohost.org";
    @GET("/dohvati_sve_pacijente.php") Call<ArrayList<Pacijent>> response();
    class Factory{
        private static PacijenteDohvatiAPI service;
        public static PacijenteDohvatiAPI getIstance(){
            if(service==null){
                Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL).build();
                service = retrofit.create(PacijenteDohvatiAPI.class);
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
    PacijenteDohvatiAPI service = retrofit.create(PacijenteDohvatiAPI.class);
}
