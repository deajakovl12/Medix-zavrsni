package com.example.deean.medix;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.deean.medix.tabs_fragments.OMedixAppFragment;
import com.example.deean.medix.tabs_fragments.PomocFragment;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class ToolbarActivityAll extends AppCompatActivity {

    protected Toolbar toolbar;
    protected DrawerBuilder result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar_activity_all);




    }

    protected Toolbar postaviToolbar(String title) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setPadding(0, 0, 0, 0);
        toolbar.setBackgroundColor(Color.rgb(162, 32, 34));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        return toolbar;

    }

    protected DrawerBuilder postaviDrawer(Toolbar toolbar2){
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withName("Početna").withIcon(getResources().getDrawable(R.drawable.home_small_white));
        SecondaryDrawerItem item2 = new SecondaryDrawerItem().withName("O Medix app").withIcon(getResources().getDrawable(R.drawable.information_white));
        SecondaryDrawerItem item3 = new SecondaryDrawerItem().withName("Pomoć").withIcon(getResources().getDrawable(R.drawable.help_small_white));

        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        item1,
                        new DividerDrawerItem(),
                        item2,
                        item3
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        switch (position) {
                            case 0:
                                getSupportActionBar().setTitle("Početna");
                                startActivity(new Intent(getApplicationContext(), Login.class));
                                //ne treba jer kad kliknem pocetni sam se makne
                                break;
                            case 2:
                                getSupportActionBar().setTitle("O Medix");
                                OMedixAppFragment f1 = new OMedixAppFragment();
                                //fragmentTransaction.add(R.id.fragment_container, f1);
                                //fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.replace(R.id.fragment_container, f1).commit();
                                break;
                            case 3:
                                getSupportActionBar().setTitle("Pomoć");
                                PomocFragment f2 = new PomocFragment();
                                //fragmentTransaction.add(R.id.fragment_container,f2);
                                //fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.replace(R.id.fragment_container, f2).commit();
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
