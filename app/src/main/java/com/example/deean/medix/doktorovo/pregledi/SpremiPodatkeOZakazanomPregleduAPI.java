package com.example.deean.medix.doktorovo.pacijenti_od_doktora.detalji_o_pacijentu;

import com.example.deean.medix.doktorovo.pacijenti_u_bazi.PacijentuDajDoktoraAPI;
import com.example.deean.medix.pacijentovo.konstruktor_i_baza.Pacijent;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Deean on 21.2.2016..
 */
public interface SpremiPodatkeOZakazanomPregleduAPI {
    String BASE_URL = "http://jaka12.heliohost.org";
    @GET("/spremi_podatke_o_pregledu.php") Call<ArrayList<Integer>> response(@Query("pac_oib") String pac_oib,@Query("datum") String datum,@Query("komentar") String komentar,@Query("id_dok") String id_dok);
    class Factory{
        private static SpremiPodatkeOZakazanomPregleduAPI service;
        public static SpremiPodatkeOZakazanomPregleduAPI getIstance(){
            if(service==null){
                Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL).build();
                service = retrofit.create(SpremiPodatkeOZakazanomPregleduAPI.class);
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
    SpremiPodatkeOZakazanomPregleduAPI service = retrofit.create(SpremiPodatkeOZakazanomPregleduAPI.class);
}
