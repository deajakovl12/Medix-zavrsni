package com.example.deean.medix;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.deean.medix.doktorovo.GetUserCallback;
import com.example.deean.medix.doktorovo.konsturktor_i_baza.Doktor;
import com.example.deean.medix.pacijentovo.GetUserCallbackPacijent;
import com.example.deean.medix.pacijentovo.konstruktor_i_baza.Pacijent;

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
    public static final String SERVER_ADDRESS = "http://jaka12.heliohost.org";

    public ServerRequest(Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Obrađujem");
        progressDialog.setMessage("Molim sačekajte...");
    }

    public ServerRequest() {

    }

    public void spremiPodatkeUPozadini(Doktor doktor, GetUserCallback doktorCallback){
//        progressDialog.show();
        new SpremiPodatkeAsyncTask(doktor,doktorCallback).execute();

    }

    public void dohvatiPodatkeUPozadini(Doktor doktor, GetUserCallback callback){
        progressDialog.show();
        new DohvatiPodatkeAsyncTask(doktor,callback).execute();
    }
    public void dohvatiEmailUpozadini(Doktor doktor, GetUserCallback emailcallback){
        //progressDialog.show();
        new DohvatiEmailAsyncTask(doktor,emailcallback).execute();
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
            dataToSend.add(new BasicNameValuePair("ime",doktor.getIme()));
            dataToSend.add(new BasicNameValuePair("prezime",doktor.getPrezime()));
            dataToSend.add(new BasicNameValuePair("adresa",doktor.getAdresa()));
            dataToSend.add(new BasicNameValuePair("oib",doktor.getOib()));
            dataToSend.add(new BasicNameValuePair("lozinka",doktor.getLozinka()));
            dataToSend.add(new BasicNameValuePair("telefon",doktor.getTelefon()));
            dataToSend.add(new BasicNameValuePair("email", doktor.getEmail()));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);//koliko cmo cekat dok se POST izvrsava
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);//vrijeme koliko zelimo cekat da dohvatimo podatke od servera

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "/spremi_doktora.php");
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
            //progressDialog.dismiss();
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
            dataToSend.add(new BasicNameValuePair("email", doktor.getEmail()));
            dataToSend.add(new BasicNameValuePair("lozinka",doktor.getLozinka()));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);//koliko cmo cekat dok se POST izvrsava
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);


            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "/dohvati_doktora.php");


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
                    String id_doktor = jObject.getString("id_doktor");
                    String ime = jObject.getString("ime");
                    String prezime = jObject.getString("prezime");
                    String adresa = jObject.getString("adresa");
                    String oib = jObject.getString("oib");
                    String telefon = jObject.getString("telefon");
                    String radno_vrijeme = jObject.getString("radno_vrijeme");
                    String rad_savjetovalista = jObject.getString("rad_savjetovalista");
                    String mobitel = jObject.getString("mobitel");
                    String titula = jObject.getString("titula");
                    //int id = jObject.getInt("id");

                    returnedDoktor = new Doktor(id_doktor,ime,prezime,adresa,oib,doktor.getLozinka(),telefon,doktor.getEmail(),radno_vrijeme,rad_savjetovalista,mobitel,titula);


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
    public class DohvatiEmailAsyncTask extends AsyncTask<Void, Void, Doktor> {//1.void-nista ne saljemo tasku dok se pokrece 2.void-kako zelimo primati progress 3.void-sto zelimo da async task vrati
        Doktor doktor;
        GetUserCallback emailCallback;

        public DohvatiEmailAsyncTask(Doktor doktor, GetUserCallback emailCallback) {
            this.doktor = doktor;
            this.emailCallback = emailCallback;
        }

        @Override
        protected Doktor doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("email", doktor.getEmail()));
            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);//koliko cmo cekat dok se POST izvrsava
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);


            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "/dohvati_email_doktora.php");


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
                    String lozinka = jObject.getString("lozinka");
                    String telefon = jObject.getString("telefon");
                    String radno_vrijeme = jObject.getString("radno_vrijeme");
                    String rad_savjetovalista = jObject.getString("rad_savjetovalista");
                    String mobitel = jObject.getString("mobitel");
                    String titula = jObject.getString("titula");

                    returnedDoktor = new Doktor(ime,prezime,adresa,oib,lozinka,telefon,doktor.getEmail(),radno_vrijeme,rad_savjetovalista,mobitel,titula);


                }

            }catch (Exception e){
                e.printStackTrace();
            }
            return returnedDoktor;
        }
        @Override
        protected void onPostExecute(Doktor returnedDoktor) {
//            progressDialog.dismiss();
            emailCallback.done(returnedDoktor);
            super.onPostExecute(returnedDoktor);
        }
    }
    public void spremiLozinkuUPozadini(Doktor doktor, GetUserCallback lozinkaCallback){
        new SpremiLozinkuAsyncTask(doktor,lozinkaCallback).execute();

    }

    public class SpremiLozinkuAsyncTask extends AsyncTask<Void, Void, Void>{//1.void-nista ne saljemo tasku dok se pokrece 2.void-kako zelimo primati progress 3.void-sto zelimo da async task vrati
        Doktor doktor;
        GetUserCallback lozinkaCallback;

        public SpremiLozinkuAsyncTask(Doktor doktor, GetUserCallback lozinkaCallback){
            this.doktor = doktor;
            this.lozinkaCallback = lozinkaCallback;
        }

        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("email",doktor.getEmail()));
            dataToSend.add(new BasicNameValuePair("lozinka",doktor.getLozinka()));


            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);//koliko cmo cekat dok se POST izvrsava
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);//vrijeme koliko zelimo cekat da dohvatimo podatke od servera

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "/promjeni_lozinku_doktor.php");
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
            lozinkaCallback.done(null);
            super.onPostExecute(aVoid);
        }
    }
    public void spremiPodatkePacijentUPozadini(Pacijent pacijent, GetUserCallbackPacijent pacijentCallback){
//        progressDialog.show();
        new SpremiPodatkePacijentAsyncTask(pacijent,pacijentCallback).execute();

    }
    public class SpremiPodatkePacijentAsyncTask extends AsyncTask<Void, Void, Void>{//1.void-nista ne saljemo tasku dok se pokrece 2.void-kako zelimo primati progress 3.void-sto zelimo da async task vrati
        Pacijent pacijent;
        GetUserCallbackPacijent pacijentCallback;

        public SpremiPodatkePacijentAsyncTask(Pacijent pacijent, GetUserCallbackPacijent pacijentCallback){
            this.pacijent = pacijent;
            this.pacijentCallback = pacijentCallback;
        }

        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("ime",pacijent.getIme()));
            dataToSend.add(new BasicNameValuePair("prezime",pacijent.getPrezime()));
            dataToSend.add(new BasicNameValuePair("adresa",pacijent.getAdresa()));
            dataToSend.add(new BasicNameValuePair("lozinka",pacijent.getLozinka()));
            dataToSend.add(new BasicNameValuePair("telefon",pacijent.getTelefon()));
            dataToSend.add(new BasicNameValuePair("oib",pacijent.getOib()));
            dataToSend.add(new BasicNameValuePair("email", pacijent.getEmail()));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);//koliko cmo cekat dok se POST izvrsava
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);//vrijeme koliko zelimo cekat da dohvatimo podatke od servera

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "/spremi_pacijenta.php");
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
//            progressDialog.dismiss();
            pacijentCallback.done(null);
            super.onPostExecute(aVoid);
        }
    }

    public void dohvatiPodatkePacijentUPozadini(Pacijent pacijent, GetUserCallbackPacijent callback){
        //progressDialog.show();
        new DohvatiPodatkePacijentAsyncTask(pacijent,callback).execute();
    }
    public class DohvatiPodatkePacijentAsyncTask extends AsyncTask<Void, Void, Pacijent> {//1.void-nista ne saljemo tasku dok se pokrece 2.void-kako zelimo primati progress 3.void-sto zelimo da async task vrati
        Pacijent pacijent;
        GetUserCallbackPacijent pacijentCallback;

        public DohvatiPodatkePacijentAsyncTask(Pacijent pacijent, GetUserCallbackPacijent pacijentCallback) {
            this.pacijent = pacijent;
            this.pacijentCallback = pacijentCallback;
        }

        @Override
        protected Pacijent doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("email", pacijent.getEmail()));
            dataToSend.add(new BasicNameValuePair("lozinka",pacijent.getLozinka()));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);//koliko cmo cekat dok se POST izvrsava
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);


            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "/dohvati_pacijenta.php");


            Pacijent returnedPacijent = null;
            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));//enkodiramo (dataToSend) i dajemo u post(da zna adresu)
                HttpResponse httpOdgovor = client.execute(post);

                HttpEntity entity = httpOdgovor.getEntity();
                String result = EntityUtils.toString(entity);
                JSONObject jObject = new JSONObject(result);
                // Log.i("Ispis:",result);
                if(jObject.length() == 0){
                    returnedPacijent = null;
                }else{
                    String ime = jObject.getString("ime");
                    String prezime = jObject.getString("prezime");
                    String adresa = jObject.getString("adresa");
                    String oib = jObject.getString("oib");
                    String telefon = jObject.getString("telefon");
                    String mobitel = jObject.getString("mobitel");
                    String spol = jObject.getString("spol");

                    returnedPacijent = new Pacijent(ime,prezime,adresa,oib,pacijent.getLozinka(),telefon,pacijent.getEmail(),spol,mobitel);


                }

            }catch (Exception e){
                e.printStackTrace();
            }
            return returnedPacijent;
        }
        @Override
        protected void onPostExecute(Pacijent returnedPacijent) {
            //progressDialog.dismiss();
            pacijentCallback.done(returnedPacijent);
            super.onPostExecute(returnedPacijent);
        }
    }
    public void dohvatiEmailUpozadiniPacijent(Pacijent pacijent, GetUserCallbackPacijent emailcallback){
       // progressDialog.show();
        new DohvatiEmailPacijentAsyncTask(pacijent,emailcallback).execute();
    }
    public class DohvatiEmailPacijentAsyncTask extends AsyncTask<Void, Void, Pacijent> {//1.void-nista ne saljemo tasku dok se pokrece 2.void-kako zelimo primati progress 3.void-sto zelimo da async task vrati
        Pacijent pacijent;
        GetUserCallbackPacijent emailCallback;

        public DohvatiEmailPacijentAsyncTask(Pacijent pacijent, GetUserCallbackPacijent emailCallback) {
            this.pacijent = pacijent;
            this.emailCallback = emailCallback;
        }

        @Override
        protected Pacijent doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("email", pacijent.getEmail()));
            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);//koliko cmo cekat dok se POST izvrsava
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);


            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "/dohvati_email_pacijenta.php");


            Pacijent returnedPacijent = null;
            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));//enkodiramo (dataToSend) i dajemo u post(da zna adresu)
                HttpResponse httpOdgovor = client.execute(post);

                HttpEntity entity = httpOdgovor.getEntity();
                String result = EntityUtils.toString(entity);
                JSONObject jObject = new JSONObject(result);
                // Log.i("Ispis:",result);
                if(jObject.length() == 0){
                    returnedPacijent = null;
                }else{
                    String ime = jObject.getString("ime");
                    String prezime = jObject.getString("prezime");
                    String adresa = jObject.getString("adresa");
                    String oib = jObject.getString("oib");
                    String lozinka = jObject.getString("lozinka");
                    String telefon = jObject.getString("telefon");
                    String mobitel = jObject.getString("mobitel");
                    String spol = jObject.getString("spol");

                    returnedPacijent = new Pacijent(ime,prezime,adresa,oib,lozinka,telefon,pacijent.getEmail(),spol,mobitel);


                }

            }catch (Exception e){
                e.printStackTrace();
            }
            return returnedPacijent;
        }
        @Override
        protected void onPostExecute(Pacijent returnedPacijent) {
            //progressDialog.dismiss();
            emailCallback.done(returnedPacijent);
            super.onPostExecute(returnedPacijent);
        }
    }
    public void spremiLozinkuUPozadiniPacijent(Pacijent pacijent, GetUserCallbackPacijent lozinkaCallback){
        new SpremiLozinkuPacijentAsyncTask(pacijent,lozinkaCallback).execute();

    }

    public class SpremiLozinkuPacijentAsyncTask extends AsyncTask<Void, Void, Void>{//1.void-nista ne saljemo tasku dok se pokrece 2.void-kako zelimo primati progress 3.void-sto zelimo da async task vrati
        Pacijent pacijent;
        GetUserCallbackPacijent lozinkaCallback;

        public SpremiLozinkuPacijentAsyncTask(Pacijent pacijent, GetUserCallbackPacijent lozinkaCallback){
            this.pacijent = pacijent;
            this.lozinkaCallback = lozinkaCallback;
        }

        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("email",pacijent.getEmail()));
            dataToSend.add(new BasicNameValuePair("lozinka",pacijent.getLozinka()));


            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);//koliko cmo cekat dok se POST izvrsava
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);//vrijeme koliko zelimo cekat da dohvatimo podatke od servera

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "/promjeni_lozinku_pacijent.php");
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
            lozinkaCallback.done(null);
            super.onPostExecute(aVoid);
        }
    }



}


