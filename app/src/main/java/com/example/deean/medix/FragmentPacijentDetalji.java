package com.example.deean.medix;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.deean.medix.tabs_fragments.LijekoveKoristiFragment;
import com.example.deean.medix.tabs_fragments.MedicinskiPodaciFragment;
import com.example.deean.medix.tabs_fragments.MjereOprezaFragment;
import com.example.deean.medix.tabs_fragments.OsnovneInformacijeFragment;
import com.example.deean.medix.tabs_fragments.OsobniPodaciFragment;

public class FragmentPacijentDetalji extends ToolbarActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private String oib;
    private int[] tabIcons = {R.drawable.osobni_podaci_white_small,R.drawable.medicinski_podaci_white_small,R.drawable.lijekovi_white_small};

    private Doktor doktor;
    DoktorLokalno DoktorLokalno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_pacijent_detalji);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);

        DoktorLokalno = new DoktorLokalno(this);

        doktor = DoktorLokalno.getPrijavljenogDoktora();
        postaviDrawer(postaviToolbar("Pacijent detalji"), doktor.ime.toUpperCase(), doktor.prezime.toUpperCase(), doktor.email).build();
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapterPacijenta adapter = new ViewPagerAdapterPacijenta(getSupportFragmentManager());
        Intent intent = getIntent();
        oib = intent.getStringExtra("ime1");
        Log.e("TAG", oib);
        adapter.addFragment(OsobniPodaciFragment.newIstance(oib), "");
        adapter.addFragment(MedicinskiPodaciFragment.newIstance(oib),"");
        adapter.addFragment(LijekoveKoristiFragment.newIstance(oib),"");
        viewPager.setAdapter(adapter);
    }
}
