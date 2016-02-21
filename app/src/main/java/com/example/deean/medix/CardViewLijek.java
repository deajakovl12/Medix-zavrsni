package com.example.deean.medix;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class CardViewLijek extends AppCompatActivity {

    TextView personName;
    ImageView personPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_card_view_lijek);
        personName = (TextView)findViewById(R.id.person_name);
        personPhoto = (ImageView)findViewById(R.id.person_photo);
    }
}
