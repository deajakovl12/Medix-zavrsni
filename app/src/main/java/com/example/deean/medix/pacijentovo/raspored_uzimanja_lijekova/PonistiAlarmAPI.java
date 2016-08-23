package com.example.deean.medix.pacijentovo.raspored_uzimanja_lijekova;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by dean on 23.08.16..
 */
public interface PonistiAlarmAPI {
    String BASE_URL = "http://jaka12.heliohost.org";
    @GET("/ponisti_alarm.php")
    Call<ArrayList<Integer>> response (@Query("req_code") String req_code);
    class Factory{
        private static PonistiAlarmAPI service;
        public static PonistiAlarmAPI getIstance(){
            if(service==null){
                Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL).build();
                service = retrofit.create(PonistiAlarmAPI.class);
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
    PonistiAlarmAPI service = retrofit.create(PonistiAlarmAPI.class);
}
