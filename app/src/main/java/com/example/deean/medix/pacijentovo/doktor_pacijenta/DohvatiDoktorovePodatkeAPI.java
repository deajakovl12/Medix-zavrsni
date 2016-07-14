package com.example.deean.medix.pacijentovo.doktor_pacijenta;


import com.example.deean.medix.doktorovo.konsturktor_i_baza.Doktor;
import com.example.deean.medix.pacijentovo.konstruktor_i_baza.Pacijent;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Deean on 21.2.2016..
 */
public interface DohvatiDoktorovePodatkeAPI {
    String BASE_URL = "http://jaka12.heliohost.org";
    @GET("/dohvati_doktorove_podatke.php") Call<ArrayList<Doktor>> response(@Query("id_doktor") String id_doktor,@Query("id_pacijent") String id_pacijent);
    class Factory{
        private static DohvatiDoktorovePodatkeAPI service;
        public static DohvatiDoktorovePodatkeAPI getIstance(){
            if(service==null){
                Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL).build();
                service = retrofit.create(DohvatiDoktorovePodatkeAPI.class);
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
    DohvatiDoktorovePodatkeAPI service = retrofit.create(DohvatiDoktorovePodatkeAPI.class);
}
