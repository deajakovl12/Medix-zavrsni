package com.example.deean.medix.doktorovo.pacijenti_u_bazi;


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
public interface PacijentuDajDoktoraAPI {
    String BASE_URL = "http://jaka12.heliohost.org";
    @GET("/pacijentu_daj_doktora.php") Call<ArrayList<Pacijent>> response(@Query("dok_id") String dok_id,@Query("pac_oib") String pac_oib);
    class Factory{
        private static PacijentuDajDoktoraAPI service;
        public static PacijentuDajDoktoraAPI getIstance(){
            if(service==null){
                Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL).build();
                service = retrofit.create(PacijentuDajDoktoraAPI.class);
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
    PacijentuDajDoktoraAPI service = retrofit.create(PacijentuDajDoktoraAPI.class);
}
