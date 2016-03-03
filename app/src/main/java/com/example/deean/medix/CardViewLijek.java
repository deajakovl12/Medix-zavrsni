package com.example.deean.medix;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class CardViewLijek extends AppCompatActivity {

    TextView lijek_ime;
    ImageView lijek_slika;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_card_view_lijek);
        lijek_ime = (TextView)findViewById(R.id.lijek_ime);
        lijek_slika = (ImageView)findViewById(R.id.lijek_slika);
    }
}
