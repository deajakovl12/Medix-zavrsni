package com.example.deean.medix;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Deean on 10.2.2016..
 */
public class ServerRequest {
    ProgressDialog progressDialog;
    public static final int CONNECTION_TIMEOUT = 1000*15;
    public static final String SERVER_ADDRESS = "http://deajakovl.heliohost.org";

    public ServerRequest(Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Obrađujem");
        progressDialog.setMessage("Molim sačekajte...");
    }
    public void spremiPodatkeUPozadini(Doktor doktor, GetUserCallback doktorCallback){
        progressDialog.show();
        new SpremiPodatkeAsyncTask(doktor,doktorCallback).execute();

    }

    public void dohvatiPodatkeUPozadini(Doktor doktor, GetUserCallback callback){
        progressDialog.show();
        new DohvatiPodatkeAsyncTask(doktor,callback).execute();
    }

    public class SpremiPodatkeAsyncTask extends AsyncTask<Void, Void, Void>{//1.void-nista ne saljemo tasku dok se pokrece 2.void-kako zelimo primati progress 3.void-sto zelimo da async task vrati
        Doktor doktor;
        GetUserCallback doktorCallback;

        public SpremiPodatkeAsyncTask(Doktor doktor, GetUserCallback doktorCallback){
            this.doktor = doktor;
            this.doktorCallback = doktorCallback;
        }

        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("ime",doktor.ime));
            dataToSend.add(new BasicNameValuePair("prezime",doktor.prezime));
            dataToSend.add(new BasicNameValuePair("adresa",doktor.adresa));
            dataToSend.add(new BasicNameValuePair("oib",doktor.oib));
            dataToSend.add(new BasicNameValuePair("lozinka",doktor.lozinka));
            dataToSend.add(new BasicNameValuePair("telefon",doktor.telefon));
            dataToSend.add(new BasicNameValuePair("email", doktor.email));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);//koliko cmo cekat dok se POST izvrsava
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);//vrijeme koliko zelimo cekat da dohvatimo podatke od servera

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "/spremi.php");
            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));//enkodiramo (dataToSend) i dajemo u post(da zna adresu)
                client.execute(post);

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            doktorCallback.done(null);
            super.onPostExecute(aVoid);
        }
    }
    public class DohvatiPodatkeAsyncTask extends AsyncTask<Void, Void, Doktor> {//1.void-nista ne saljemo tasku dok se pokrece 2.void-kako zelimo primati progress 3.void-sto zelimo da async task vrati
        Doktor doktor;
        GetUserCallback doktorCallback;

        public DohvatiPodatkeAsyncTask(Doktor doktor, GetUserCallback doktorCallback) {
            this.doktor = doktor;
            this.doktorCallback = doktorCallback;
        }

        @Override
        protected Doktor doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("email", doktor.email));
            dataToSend.add(new BasicNameValuePair("lozinka",doktor.lozinka));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);//koliko cmo cekat dok se POST izvrsava
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);


            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "/dohvati.php");


            Doktor returnedDoktor = null;
            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));//enkodiramo (dataToSend) i dajemo u post(da zna adresu)
                HttpResponse httpOdgovor = client.execute(post);

                HttpEntity entity = httpOdgovor.getEntity();
                String result = EntityUtils.toString(entity);
                JSONObject jObject = new JSONObject(result);
               // Log.i("Ispis:",result);
                if(jObject.length() == 0){
                    returnedDoktor = null;
                }else{
                    String ime = jObject.getString("ime");
                    String prezime = jObject.getString("prezime");
                    String adresa = jObject.getString("adresa");
                    String oib = jObject.getString("oib");
                    String telefon = jObject.getString("telefon");
                    String radno_vrijeme = jObject.getString("radno_vrijeme");
                    String rad_savjetovalista = jObject.getString("rad_savjetovalista");
                    String mobitel = jObject.getString("mobitel");
                    String titula = jObject.getString("titula");

                    returnedDoktor = new Doktor(ime,prezime,adresa,oib,doktor.lozinka,telefon,doktor.email,radno_vrijeme,rad_savjetovalista,mobitel,titula);


                }

            }catch (Exception e){
                e.printStackTrace();
            }
            return returnedDoktor;
        }
        @Override
        protected void onPostExecute(Doktor returnedDoktor) {
            progressDialog.dismiss();
            doktorCallback.done(returnedDoktor);
            super.onPostExecute(returnedDoktor);
        }
    }

}
