package com.example.deean.medix.pacijentovo.raspored_uzimanja_lijekova;
import com.example.deean.medix.pacijentovo.konstruktor_i_baza.Pacijent;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AlarmiAPI {
    String BASE_URL = "http://jaka12.heliohost.org";
    @GET("/dohvati_alarme.php")
    Call<ArrayList<AlarmKlasa>> response (@Query("pac_id") String pac_id);
    class Factory{
        private static AlarmiAPI service;
        public static AlarmiAPI getIstance(){
            if(service==null){
                Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL).build();
                service = retrofit.create(AlarmiAPI.class);
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
    AlarmiAPI service = retrofit.create(AlarmiAPI.class);
}
