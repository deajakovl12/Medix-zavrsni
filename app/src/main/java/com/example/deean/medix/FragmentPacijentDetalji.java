package com.example.deean.medix;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class FragmentPacijentDetalji extends AppCompatActivity {

    private String oib;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_pacijent_detalji);

        Intent intent = getIntent();
        oib = intent.getStringExtra("ime1");
        Log.e("TAG", oib);
    }
}
