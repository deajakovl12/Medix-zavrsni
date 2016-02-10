package com.example.deean.medix;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by Deean on 10.2.2016..
 */
public class ServerRequest {
    ProgressDialog progressDialog;
    public static final int CONNECTION_TIMEOUT = 1000*15;
    public static final String SERVER_ADDRESS = "http://deajakovl.host56.com";

    public ServerRequest(Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Obrađujem");
        progressDialog.setMessage("Molim sačekajte...");
    }
    public void spremiPodatkeUPozadini(Doktor doktor, GetUserCallback callback){
        progressDialog.show();
        new SpremiPodatkeAsyncTask().execute();

    }

    public void dohvatiPodatkeUPozadini(Doktor doktor, GetUserCallback callback){
        progressDialog.show();
    }

    public class SpremiPodatkeAsyncTask extends AsyncTask<Void,Void,Void>{//1.void-nista ne saljemo tasku dok se pokrece 2.void-kako zelimo primati progress 3.void-sto zelimo da async task vrati
        Doktor doktor;
        GetUserCallback doktorCallback;
        public SpremiPodatkeAsyncTask(){

        }

        @Override
        protected Void doInBackground(Void... params) {

            return null;
        }
    }
}
