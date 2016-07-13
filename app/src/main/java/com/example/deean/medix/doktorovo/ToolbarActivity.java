package com.example.deean.medix.doktorovo;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.deean.medix.R;
import com.example.deean.medix.doktorovo.pacijenti_od_doktora.RecycleViewPacijenta;
import com.example.deean.medix.doktorovo.pomoc.PomocDoktor;
import com.example.deean.medix.doktorovo.postavke_profila.PostavkeProfilaDoktora;
import com.example.deean.medix.doktorovo.pregledi.PreglediOdDoktora;
import com.example.deean.medix.lijekovi.RecycleView;
import com.example.deean.medix.pocetni_zaslon.Login;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class ToolbarActivity extends AppCompatActivity {

    protected Toolbar toolbar;
    protected DrawerBuilder result;
    //protected DrawerBuilder result1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);

        //result1  = postaviDrawer(postaviToolbar("Medix"),null,null,null);



    }
    protected Toolbar postaviToolbar(String title) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setPadding(0, 0, 0, 0);
        toolbar.setBackgroundColor(Color.rgb(162, 32, 34));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        return toolbar;
    }

    protected DrawerBuilder postaviDrawer(Toolbar toolbar2, String ime, String prezime, String email,String spol){

        AccountHeader headerResult = null;
        if(spol.equals("Musko")) {
             headerResult = new AccountHeaderBuilder()
                    .withActivity(this)
                    .withHeaderBackground(R.drawable.pozadina)
                    .addProfiles(
                            new ProfileDrawerItem().withName(ime + " " + prezime).withEmail(email).withIcon(getResources().getDrawable(R.drawable.doctor))
                    )
                    .withSelectionListEnabledForSingleProfile(false)
                    .build();
        }
        else if(spol.equals("Zensko")){
             headerResult = new AccountHeaderBuilder()
                    .withActivity(this)
                    .withHeaderBackground(R.drawable.pozadina)
                    .addProfiles(
                            new ProfileDrawerItem().withName(ime + " " + prezime).withEmail(email).withIcon(getResources().getDrawable(R.drawable.doktorica_white))
                    )
                    .withSelectionListEnabledForSingleProfile(false)
                    .build();
        }

        PrimaryDrawerItem item1 = null;
        if(spol.equals("Musko")) {
             item1 = new PrimaryDrawerItem().withName("Profil").withIcon(getResources().getDrawable(R.drawable.doctor_white_small));
        }
        else if(spol.equals("Zensko")){
             item1 = new PrimaryDrawerItem().withName("Profil").withIcon(getResources().getDrawable(R.drawable.doktorica_white_small));
        }
        SecondaryDrawerItem item2 = new SecondaryDrawerItem().withName("Lijekovi").withIcon(getResources().getDrawable(R.drawable.lijekovi_white_small));
        SecondaryDrawerItem item3 = new SecondaryDrawerItem().withName("Pacijenti").withIcon(getResources().getDrawable(R.drawable.patient_white_small));
        SecondaryDrawerItem item4 = new SecondaryDrawerItem().withName("Zakazani pregledi").withIcon(getResources().getDrawable(R.drawable.pregled_white_small));
        SecondaryDrawerItem item5 = new SecondaryDrawerItem().withName("Postavke profila").withIcon(getResources().getDrawable(R.drawable.postavke_white_small));
        SecondaryDrawerItem item6 = new SecondaryDrawerItem().withName("Pomoć").withIcon(getResources().getDrawable(R.drawable.help_small_white));
        SecondaryDrawerItem item7 = new SecondaryDrawerItem().withName("Odjava").withIcon(getResources().getDrawable(R.drawable.logout_white_small));
        result = new DrawerBuilder()
                .withAccountHeader(headerResult)
                .withActivity(this)
                //.withTranslucentStatusBar(false)
                //.withActionBarDrawerToggle(false)
                .withToolbar(toolbar2)
                .addDrawerItems(
                        item1,
                        new DividerDrawerItem(),
                        item2,
                        new DividerDrawerItem(),
                        item3,
                        item4,
                        new DividerDrawerItem(),
                        item5,
                        item6,
                        item7

                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        switch (position) {
                            case 1:
                                startActivity(new Intent(getApplicationContext(),Prijava.class));
                                break;
                            case 2:
                                break;
                            case 3:
                                startActivity(new Intent(getApplicationContext(),RecycleView.class));
                                break;
                            case 5:
                                startActivity(new Intent(getApplicationContext(),RecycleViewPacijenta.class));
                                break;
                            case 6:
                                startActivity(new Intent(getApplicationContext(),PreglediOdDoktora.class));
                                break;
                            case 8:
                                startActivity(new Intent(getApplicationContext(), PostavkeProfilaDoktora.class));
                                break;
                            case 9:
                                startActivity(new Intent(getApplicationContext(), PomocDoktor.class));
                                break;
                            case 10:
                                startActivity(new Intent(getApplicationContext(), Login.class));
                                break;
                        }
                        Log.e("Pozicija", String.valueOf(position));
                        return false;
                    }
                });
        //.build();
        return result;

    }
}
