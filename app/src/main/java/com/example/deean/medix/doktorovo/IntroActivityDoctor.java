package com.example.deean.medix.doktorovo;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.deean.medix.IntroFragment;
import com.example.deean.medix.R;
import com.example.deean.medix.doktorovo.konsturktor_i_baza.DoktorLokalno;
import com.example.deean.medix.pacijentovo.konstruktor_i_baza.PacijentLokalno;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by dean on 24.08.16..
 */
public class IntroActivityDoctor extends AppCompatActivity {

    public static final String FIRST = "FIRST";
    public static final String SECOND = "SECOND";
    public static final String THIRD = "THIRD";
    public static final String LAST = "LAST";

    public static final String FIRST_P = "FIRST_p";
    public static final String SECOND_P = "SECOND_P";
    public static final String THIRD_P = "THIRD_P";
    public static final String LAST_P = "LAST_P";

    DoktorLokalno doktorLokalno;
    PacijentLokalno pacijentLokalno;

    private ViewPager viewPager;
    private CircleIndicator indicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

//        getSupportActionBar().hide();

        pacijentLokalno = new PacijentLokalno(this);
        doktorLokalno = new DoktorLokalno(this);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        indicator = (CircleIndicator) findViewById(R.id.indicator);
        setupViewPager(viewPager);

    }
    private void setupViewPager(ViewPager viewPager) {
        IntroAdapter adapter = new IntroAdapter(getSupportFragmentManager());

        if(doktorLokalno.provjeriPrijavljenogDoktora()) {
            adapter.addFragment(IntroFragment.newIstance(FIRST), "First Intro Screen");
            adapter.addFragment(IntroFragment.newIstance(SECOND), "Second Intro Screen");
            adapter.addFragment(IntroFragment.newIstance(THIRD), "Third Intro Screen");
            adapter.addFragment(IntroFragment.newIstance(LAST), "Last Intro Screen");
        }
        else if(pacijentLokalno.provjeriPrijavljenogPacijenta()){
            adapter.addFragment(IntroFragment.newIstance(FIRST_P), "First Intro ScreenP");
            adapter.addFragment(IntroFragment.newIstance(SECOND_P), "Second Intro ScreenP");
            adapter.addFragment(IntroFragment.newIstance(THIRD_P), "Third Intro ScreenP");
            adapter.addFragment(IntroFragment.newIstance(LAST_P), "Last Intro ScreenP");
        }
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);
        adapter.registerDataSetObserver(indicator.getDataSetObserver());
    }
}
