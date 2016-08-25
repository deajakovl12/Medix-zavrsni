package com.example.deean.medix.doktorovo;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.deean.medix.IntroFragment;
import com.example.deean.medix.R;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by dean on 24.08.16..
 */
public class IntroActivityDoctor extends AppCompatActivity {

    public static final String FIRST = "FIRST";
    public static final String SECOND = "SECOND";
    public static final String THIRD = "THIRD";
    public static final String LAST = "LAST";


    private ViewPager viewPager;
    private CircleIndicator indicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

//        getSupportActionBar().hide();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        indicator = (CircleIndicator) findViewById(R.id.indicator);
        setupViewPager(viewPager);

    }
    private void setupViewPager(ViewPager viewPager) {
        IntroAdapter adapter = new IntroAdapter(getSupportFragmentManager());
        adapter.addFragment(IntroFragment.newIstance(FIRST), "First Intro Screen");
        adapter.addFragment(IntroFragment.newIstance(SECOND), "Second Intro Screen");
        adapter.addFragment(IntroFragment.newIstance(THIRD), "Third Intro Screen");
        adapter.addFragment(IntroFragment.newIstance(LAST), "Last Intro Screen");
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);
        adapter.registerDataSetObserver(indicator.getDataSetObserver());
    }
}
