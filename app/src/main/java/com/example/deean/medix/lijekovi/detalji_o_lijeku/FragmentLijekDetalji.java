package com.example.deean.medix.lijekovi.detalji_o_lijeku;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;

import com.example.deean.medix.doktorovo.konsturktor_i_baza.Doktor;
import com.example.deean.medix.doktorovo.konsturktor_i_baza.DoktorLokalno;
import com.example.deean.medix.R;
import com.example.deean.medix.doktorovo.ToolbarActivity;
import com.example.deean.medix.pacijentovo.konstruktor_i_baza.Pacijent;
import com.example.deean.medix.pacijentovo.konstruktor_i_baza.PacijentLokalno;
import com.example.deean.medix.registracija.ViewPagerAdapter;

public class FragmentLijekDetalji extends ToolbarActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private String ime;
    private int[] tabIcons = {R.drawable.caution_white,R.drawable.information_white};

    private Doktor doktor;
    com.example.deean.medix.doktorovo.konsturktor_i_baza.DoktorLokalno DoktorLokalno;

    private Pacijent pacijent;
    PacijentLokalno pacijentLokalno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_lijek_detalji);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(tabIcons[1]);
        tabLayout.getTabAt(1).setIcon(tabIcons[0]);

        DoktorLokalno = new DoktorLokalno(this);
        pacijentLokalno = new PacijentLokalno(this);

        doktor = DoktorLokalno.getPrijavljenogDoktora();
        pacijent = pacijentLokalno.getPrijavljenogPacijenta();

        if(DoktorLokalno.provjeriPrijavljenogDoktora()) {
            postaviDrawer(postaviToolbar("Lijek detalji"), doktor.getIme().toUpperCase(), doktor.getPrezime().toUpperCase(), doktor.getEmail(), doktor.getSpol()).build();
        }
        else if(pacijentLokalno.provjeriPrijavljenogPacijenta()){
            postaviDrawer(postaviToolbar("Dobiveni lijekovi"),pacijent.getIme(),pacijent.getPrezime(),pacijent.getEmail()).build();
        }
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        Intent intent = getIntent();
        ime = intent.getStringExtra("ime1");
        Log.e("TAG", ime);
        adapter.addFragment(OsnovneInformacijeFragment.newIstance(ime),"Osnovne informacije");
        adapter.addFragment(MjereOprezaFragment.newIstance(ime),"Mjere opreza");
        viewPager.setAdapter(adapter);
    }
}
