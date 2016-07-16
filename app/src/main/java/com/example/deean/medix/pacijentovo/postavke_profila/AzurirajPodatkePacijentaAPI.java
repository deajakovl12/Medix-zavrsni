package com.example.deean.medix.pacijentovo.postavke_profila;

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
public interface AzurirajPodatkePacijentaAPI {
    String BASE_URL = "http://jaka12.heliohost.org";
    @GET("/spremi_nove_podatke_o_pacijentu.php") Call<ArrayList<Void>> response(@Query("pac_id") String pac_id,@Query("ime") String ime,@Query("prezime") String prezime,@Query("adresa") String adresa,@Query("telefon") String telefon,@Query("mobitel") String mobitel,@Query("spol") String spol);
    class Factory{
        private static AzurirajPodatkePacijentaAPI service;
        public static AzurirajPodatkePacijentaAPI getIstance(){
            if(service==null){
                Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL).build();
                service = retrofit.create(AzurirajPodatkePacijentaAPI.class);
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
    AzurirajPodatkePacijentaAPI service = retrofit.create(AzurirajPodatkePacijentaAPI.class);
}
