package com.example.deean.medix;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Random;


public class ZaboravljenaLozinka extends AppCompatActivity implements View.OnClickListener {
    Button bPosalji;
    EditText lozEmail;
    private static final String username = "medix.supp@gmail.com";
    private static final String password = "docse1viv1";

    DoktorLokalno DoktorLokalno;
    PacijentLokalno PacijentLokalno;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_zaboravljena_lozinka);
        lozEmail = (EditText) findViewById(R.id.lozEmail);
        bPosalji = (Button) findViewById(R.id.bPosalji);
        bPosalji.setOnClickListener(this);
        DoktorLokalno = new DoktorLokalno(this);
        PacijentLokalno = new PacijentLokalno(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bPosalji:
                String email = lozEmail.getText().toString();

                Doktor doktor = new Doktor(email);
                Pacijent pacijent = new Pacijent(email);

                autentifikacija_doktora(doktor, pacijent);


                DoktorLokalno.spremiDoktorPodatke(doktor);
                PacijentLokalno.spremiPacijentPodatke(pacijent);

                break;
        }

    }
    private void sendMail(String email, String subject, String messageBody) {
        Session session = createSessionObject();

        try {
            Message message = createMessage(email, subject, messageBody, session);
            new SendMailTask().execute(message);
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    private Message createMessage(String email, String subject, String messageBody, Session session) throws MessagingException, UnsupportedEncodingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("medix.supp@gmail.com", "Medix Support"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email, email));
        message.setSubject(subject);
        message.setText(messageBody);
        return message;
    }

    private Session createSessionObject() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        return Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    private class SendMailTask extends AsyncTask<Message, Void, Void> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(ZaboravljenaLozinka.this, "Molim sačekajte", "Šaljem mail...", true, false);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
        }

        @Override
        protected Void doInBackground(Message... messages) {
            try {
                Transport.send(messages[0]);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    private void autentifikacija_doktora(Doktor doktor, final Pacijent pacijent){
        ServerRequest serverRequest = new ServerRequest(this);
        serverRequest.dohvatiEmailUpozadini(doktor, new GetUserCallback() {
            @Override
            public void done(Doktor returnedDoktor) {
                if(returnedDoktor == null){
                    autentifikacija_pacijenta(pacijent);
                }else{
                    prikaziPoruku_doktor();
                }
            }
        });

    }
    private void autentifikacija_pacijenta(Pacijent pacijent){
        ServerRequest serverRequest = new ServerRequest(this);
        serverRequest.dohvatiEmailUpozadiniPacijent(pacijent, new GetUserCallbackPacijent() {
            @Override
            public void done(Pacijent returnedPacijent) {
                if (returnedPacijent == null) {
                    showErrorMessage();
                } else {
                    prikaziPoruku_Pacijent();
                }
            }
        });

    }
    private void showErrorMessage(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ZaboravljenaLozinka.this);
        dialogBuilder.setMessage("Račun s takvim E-mailom ne postoji!");
        dialogBuilder.setPositiveButton("Ok", null);
        dialogBuilder.show();

    }

    private void prikaziPoruku_doktor(){
        String email = lozEmail.getText().toString();
        String subject = "Zahtjev za promjenom lozinke";
        String message = "Nova lozinka glasi: ";
        AlertDialog.Builder dialogBuilder1 = new AlertDialog.Builder(ZaboravljenaLozinka.this);
        dialogBuilder1.setMessage("E-mail s novom lozinkom vam je poslan!");
        dialogBuilder1.show();
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random rnd = new Random();
        StringBuilder lozinka = new StringBuilder(6);
        for( int i = 0; i < 6; i++ )
                lozinka.append(AB.charAt(rnd.nextInt(AB.length())));
        message = message + lozinka.toString();
        Doktor doktor  = new Doktor(email, lozinka.toString());
        sendMail(email, subject, message);
        promjeniLozinku_doktor(doktor);
    }
    private void promjeniLozinku_doktor(Doktor doktor){
        ServerRequest serverRequest = new ServerRequest(this);
        serverRequest.spremiLozinkuUPozadini(doktor, new GetUserCallback() {
            @Override
            public void done(Doktor returnedDoktor) {
                startActivity(new Intent(ZaboravljenaLozinka.this,Login.class));
            }
        });
    }
    private void prikaziPoruku_Pacijent(){
        String email = lozEmail.getText().toString();
        String subject = "Zahtjev za promjenom lozinke";
        String message = "Nova lozinka glasi: ";
        AlertDialog.Builder dialogBuilder1 = new AlertDialog.Builder(ZaboravljenaLozinka.this);
        dialogBuilder1.setMessage("E-mail s novom lozinkom vam je poslan!");
        dialogBuilder1.show();
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random rnd = new Random();
        StringBuilder lozinka = new StringBuilder(6);
        for( int i = 0; i < 6; i++ )
            lozinka.append(AB.charAt(rnd.nextInt(AB.length())));
        message = message + lozinka.toString();
        Pacijent pacijent  = new Pacijent(email, lozinka.toString());
        sendMail(email, subject, message);
        promjeniLozinku_pacijent(pacijent);
    }
    private void promjeniLozinku_pacijent(Pacijent pacijent){
        ServerRequest serverRequest = new ServerRequest(this);
        serverRequest.spremiLozinkuUPozadiniPacijent(pacijent, new GetUserCallbackPacijent() {
            @Override
            public void done(Pacijent returnedPacijent) {
                startActivity(new Intent(ZaboravljenaLozinka.this,Login.class));
            }
        });
        }
}
