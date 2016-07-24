package com.example.deean.medix.registracija;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.deean.medix.doktorovo.konsturktor_i_baza.Doktor;
import com.example.deean.medix.doktorovo.GetUserCallback;
import com.example.deean.medix.pacijentovo.GetUserCallbackPacijent;
import com.example.deean.medix.pocetni_zaslon.Login;
import com.example.deean.medix.pacijentovo.konstruktor_i_baza.Pacijent;
import com.example.deean.medix.R;
import com.example.deean.medix.ServerRequest;
import com.github.ybq.android.spinkit.SpinKitView;

/**
 * Created by Deean on 4.3.2016..
 */
public class PacijentRegistracijaFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    private static  final String ARG_EXAMPLE = "this_is_a_constant";
    private String podatak;

    ProgressDialog progressDialog;

    Button bRegistracija;
    SpinKitView skw;
    EditText etEmail, etLozinka, etOIB, etTelefon, etAdresa, etIme, etPrezime;


    public PacijentRegistracijaFragment(){
    }
    public static PacijentRegistracijaFragment newIstance (String example_argument){
        PacijentRegistracijaFragment pacijentRegistracijaFragment = new PacijentRegistracijaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_EXAMPLE, example_argument);
        pacijentRegistracijaFragment.setArguments(args);
        return pacijentRegistracijaFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        podatak = getArguments().getString(ARG_EXAMPLE);
        Log.i("Fragment created with", podatak);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LayoutInflater lf = getActivity().getLayoutInflater();
        View view = lf.inflate(R.layout.fragment_pacijent_registracija, container, false);


        etEmail = (EditText) view.findViewById(R.id.etEmail);
        etLozinka = (EditText) view.findViewById(R.id.etLozinka);
        etOIB = (EditText) view.findViewById(R.id.etOIB);
        etTelefon = (EditText) view.findViewById(R.id.etTelefon);
        etAdresa = (EditText) view.findViewById(R.id.etAdresa);
        etIme = (EditText) view.findViewById(R.id.etIme);
        etPrezime = (EditText) view.findViewById(R.id.etPrezime);
        bRegistracija = (Button) view.findViewById(R.id.bRegistracija);
        skw = (SpinKitView) view.findViewById(R.id.loading_view);

        bRegistracija.setOnClickListener(this);



        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bRegistracija:
                String ime = etIme.getText().toString();
                String prezime = etPrezime.getText().toString();
                String email = etEmail.getText().toString();
                String telefon = etTelefon.getText().toString();
                String adresa = etAdresa.getText().toString();
                String oib = etOIB.getText().toString();
                String lozinka = etLozinka.getText().toString();
                if(email.contains("@")) {

                    /*progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setCancelable(false);
                    progressDialog.setTitle("Registriram");
                    progressDialog.setMessage("Molim sačekajte...");
                    progressDialog.show();*/

                    bRegistracija.setVisibility(View.GONE);
                    skw.setVisibility(View.VISIBLE);

                    final Pacijent pacijent3 = new Pacijent(ime, prezime, adresa, oib, lozinka, telefon, email, null, null);
                    Doktor doktor2 = new Doktor (email);
                    final Pacijent pacijent2 = new Pacijent(email);

                    final ServerRequest serverRequest = new ServerRequest();
                    serverRequest.dohvatiEmailUpozadini(doktor2, new GetUserCallback() {
                        @Override
                        public void done(Doktor returnedDoktor) {
                            if (returnedDoktor == null) {
                                serverRequest.dohvatiEmailUpozadiniPacijent(pacijent2, new GetUserCallbackPacijent() {
                                    @Override
                                    public void done(Pacijent returnedPacijent) {
                                        if (returnedPacijent == null) {
                                            //progressDialog.dismiss();
                                            bRegistracija.setVisibility(View.VISIBLE);
                                            skw.setVisibility(View.GONE);
                                            registrirajPacijent(pacijent3);
                                        } else {
                                            bRegistracija.setVisibility(View.VISIBLE);
                                            skw.setVisibility(View.GONE);
                                            //progressDialog.dismiss();
                                            showErrorMessage();

                                        }
                                    }
                                });
                            } else {
                                //progressDialog.dismiss();
                                showErrorMessage();
                                bRegistracija.setVisibility(View.VISIBLE);
                                skw.setVisibility(View.GONE);
                            }
                        }
                    });
                }
                else{
                    /*AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                    dialogBuilder.setTitle("Neispravni E-mail");
                    dialogBuilder.setMessage("Nije ispravan E-Mail, unesite extenziju (npr. @gmail.com)");
                    dialogBuilder.setPositiveButton("Ok", null);
                    dialogBuilder.show();*/
                    Toast.makeText(getActivity(), "Nije ispravan E-mail, unesite extenziju (npr. @gmail.com)!", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }


    private void registrirajPacijent(Pacijent pacijent) {
        ServerRequest serverRequest = new ServerRequest();
        serverRequest.spremiPodatkePacijentUPozadini(pacijent, new GetUserCallbackPacijent() {
            @Override
            public void done(Pacijent returnedPacijent) {
                potvrdnaPoruka();
            }
        });
    }
    private void showErrorMessage(){
        /*AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setTitle("Postojeći E-mail");
        dialogBuilder.setMessage("Račun s takvim E-mailom je već kreiran!");
        dialogBuilder.setPositiveButton("Ok", null);
        dialogBuilder.show();*/
        Toast.makeText(getActivity(), "Račun s takvim E-mailom je već kreiran!", Toast.LENGTH_SHORT).show();


    }
    private void potvrdnaPoruka(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setTitle("Uspješna registracija");
        dialogBuilder.setMessage("Kreirali ste račun, nastavite s prijavom");
        dialogBuilder.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                startActivity(new Intent(getActivity(), Login.class));
            }
        });
        dialogBuilder.show();
    }


}
