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
public interface PacijentuDajLijekAPI {
    String BASE_URL = "http://jaka12.heliohost.org";
    @GET("/pacijentu_daj_lijek.php") Call<ArrayList<Boolean>> response(@Query("lijek_naziv") String lijek_naziv,@Query("pac_id") String pac_id);
    class Factory{
        private static PacijentuDajLijekAPI service;
        public static PacijentuDajLijekAPI getIstance(){
            if(service==null){
                Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL).build();
                service = retrofit.create(PacijentuDajLijekAPI.class);
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
    PacijentuDajLijekAPI service = retrofit.create(PacijentuDajLijekAPI.class);
}
