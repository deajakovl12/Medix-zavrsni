package com.example.deean.medix.lijekovi;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by Deean on 21.2.2016..
 */
public interface LijekAPI {
    String BASE_URL = "http://jaka12.heliohost.org";
    @GET("/dohvati_lijek.php") Call<ArrayList<Lijek>> response();
    class Factory{
        private static LijekAPI service;
        public static LijekAPI getIstance(){
            if(service==null){
                Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL).build();
                service = retrofit.create(LijekAPI.class);
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
    LijekAPI service = retrofit.create(LijekAPI.class);
}
