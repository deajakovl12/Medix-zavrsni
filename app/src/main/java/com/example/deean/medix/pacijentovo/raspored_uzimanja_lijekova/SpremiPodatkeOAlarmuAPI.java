package com.example.deean.medix.pacijentovo.raspored_uzimanja_lijekova;

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
public interface SpremiPodatkeOAlarmuAPI {
    String BASE_URL = "http://jaka12.heliohost.org";
    @GET("/spremi_alarm.php") Call<ArrayList<Integer>> response(@Query("unique_code") String unique_code,@Query("id_pacijent") String id_pac,@Query("id_lijek") String id_lijek,@Query("vrijeme_pocetka_uzimanja") String vpu,@Query("uzimati_svakih") String us);
    class Factory{
        private static SpremiPodatkeOAlarmuAPI service;
        public static SpremiPodatkeOAlarmuAPI getIstance(){
            if(service==null){
                Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL).build();
                service = retrofit.create(SpremiPodatkeOAlarmuAPI.class);
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
    SpremiPodatkeOAlarmuAPI service = retrofit.create(SpremiPodatkeOAlarmuAPI.class);
}
