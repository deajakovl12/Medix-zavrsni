package com.example.deean.medix;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.deean.medix.tabs_fragments.DoktorRegistracijaFragment;
import com.example.deean.medix.tabs_fragments.MjereOprezaFragment;
import com.example.deean.medix.tabs_fragments.OsnovneInformacijeFragment;
import com.example.deean.medix.tabs_fragments.PacijentRegistracijaFragment;
import com.mikepenz.materialdrawer.Drawer;

public class FragmentRegistracija extends ToolbarActivityAll {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    Drawer rezultat;

    private int[] tabIcons = {R.drawable.patient_white_small,R.drawable.doctor_white_small};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_registracija);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(tabIcons[1]);
        tabLayout.getTabAt(1).setIcon(tabIcons[0]);

        rezultat = postaviDrawer(postaviToolbar("Registracija")).build();
        rezultat.removeItemByPosition(2);
        rezultat.removeItemByPosition(2);

    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(DoktorRegistracijaFragment.newIstance("Za doktora"),"Doktor");
        adapter.addFragment(PacijentRegistracijaFragment.newIstance("Za pacijenta"), "Pacijent");
        viewPager.setAdapter(adapter);
    }
}
