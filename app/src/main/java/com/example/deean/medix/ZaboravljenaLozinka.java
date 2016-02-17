package com.example.deean.medix;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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


public class ZaboravljenaLozinka extends AppCompatActivity implements View.OnClickListener {
    Button bPosalji;
    EditText lozEmail;
    private static final String username = "brutallion1@gmail.com";
    private static final String password = "docse1viv";

    DoktorLokalno DoktorLokalno;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_zaboravljena_lozinka);
        lozEmail = (EditText) findViewById(R.id.lozEmail);
        bPosalji = (Button) findViewById(R.id.bPosalji);
        bPosalji.setOnClickListener(this);
        DoktorLokalno = new DoktorLokalno(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bPosalji:
                String email = lozEmail.getText().toString();

                Doktor doktor = new Doktor(email);

                autentifikacija(doktor);
                DoktorLokalno.spremiDoktorPodatke(doktor);

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
        message.setFrom(new InternetAddress("brutallion1@gmail.com", "Dean Jakovljevic"));
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


    private void autentifikacija(Doktor doktor){
        ServerRequest serverRequest = new ServerRequest(this);
        serverRequest.dohvatiEmailUpozadini(doktor, new GetUserCallback() {
            @Override
            public void done(Doktor returnedDoktor) {
                if(returnedDoktor == null){
                    showErrorMessage();
                }else{
                    prikaziPoruku();
                }
            }
        });

    }

    private void showErrorMessage(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ZaboravljenaLozinka.this);
        dialogBuilder.setMessage("Račun s takvim E-mailom ne postoji!");
        dialogBuilder.setPositiveButton("Ok",null);
        dialogBuilder.show();

    }

    private void prikaziPoruku(){
        String email = lozEmail.getText().toString();
        String subject = "Zahtjev za promjenom lozinke";
        String message = "U poruci vam je dostupna nova lozinka";
        AlertDialog.Builder dialogBuilder1 = new AlertDialog.Builder(ZaboravljenaLozinka.this);
        dialogBuilder1.setMessage("E-mail s upustvima je poslan!");
        dialogBuilder1.setPositiveButton("Ok", null);
        dialogBuilder1.show();
        sendMail(email, subject, message);
    }
}
