package com.example.deean.medix.pacijentovo.pregledi_pacijenta;
import com.example.deean.medix.doktorovo.pregledi.Pregledi;
import com.example.deean.medix.pacijentovo.konstruktor_i_baza.Pacijent;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PreglediPacijentaAPI {
    String BASE_URL = "http://jaka12.heliohost.org";
    @GET("/dohvati_preglede_pacijenta.php")
    Call<ArrayList<Pregledi>> response (@Query("pac_id") String pac_id,@Query("dok_id") String dok_id);
    class Factory{
        private static PreglediPacijentaAPI service;
        public static PreglediPacijentaAPI getIstance(){
            if(service==null){
                Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL).build();
                service = retrofit.create(PreglediPacijentaAPI.class);
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
    PreglediPacijentaAPI service = retrofit.create(PreglediPacijentaAPI.class);
}
