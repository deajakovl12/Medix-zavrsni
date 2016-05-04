package com.example.deean.medix.doktorovo.pacijenti_od_doktora.detalji_o_pacijentu;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Deean on 21.2.2016..
 */
public interface PacijentuPromjeniMedicinskePodatkeAPI {
    String BASE_URL = "http://jaka12.heliohost.org";
    @GET("/pacijentu_promjeni_medicinske_podatke.php") Call<ArrayList<Void>> response(@Query("pac_oib") String pac_oib,@Query("pac_bolesti") String pac_bolesti,@Query("pac_lab_pod") String pac_lab_pod);
    class Factory{
        private static PacijentuPromjeniMedicinskePodatkeAPI service;
        public static PacijentuPromjeniMedicinskePodatkeAPI getIstance(){
            if(service==null){
                Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL).build();
                service = retrofit.create(PacijentuPromjeniMedicinskePodatkeAPI.class);
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
    PacijentuPromjeniMedicinskePodatkeAPI service = retrofit.create(PacijentuPromjeniMedicinskePodatkeAPI.class);
}
