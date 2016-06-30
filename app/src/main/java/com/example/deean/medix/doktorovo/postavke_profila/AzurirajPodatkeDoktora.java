package com.example.deean.medix.doktorovo.postavke_profila;

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
public interface AzurirajPodatkeDoktora {
    String BASE_URL = "http://jaka12.heliohost.org";
    @GET("/spremi_nove_podatke_o_doktoru.php") Call<ArrayList<Integer>> response(@Query("dok_id") String dok_id,@Query("ime") String ime,@Query("prezime") String prezime,@Query("titula") String titula,@Query("radno_vrijeme") String radno_vrijeme,@Query("rad_savjetovalista") String rad_savjetovalista,@Query("adresa") String adresa,@Query("telefon") String telefon,@Query("mobitel") String mobitel);
    class Factory{
        private static AzurirajPodatkeDoktora service;
        public static AzurirajPodatkeDoktora getIstance(){
            if(service==null){
                Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL).build();
                service = retrofit.create(AzurirajPodatkeDoktora.class);
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
    AzurirajPodatkeDoktora service = retrofit.create(AzurirajPodatkeDoktora.class);
}
