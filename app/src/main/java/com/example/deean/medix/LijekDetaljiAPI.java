package com.example.deean.medix;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Deean on 21.2.2016..
 */
public interface LijekDetaljiAPI {
    String BASE_URL = "http://jaka12.heliohost.org";

    @GET("/dohvati_lijek_detalji.php")
    Call<ArrayList<Lijek>> response (@Query("lijek_ime") String lijek_ime);

    class Factory{
        private static LijekDetaljiAPI service;
        public static LijekDetaljiAPI getIstance(){
            if(service==null){
                Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL).build();
                service = retrofit.create(LijekDetaljiAPI.class);
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
    LijekDetaljiAPI service = retrofit.create(LijekDetaljiAPI.class);
}
