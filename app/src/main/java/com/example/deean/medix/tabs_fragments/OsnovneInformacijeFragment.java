package com.example.deean.medix.tabs_fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deean.medix.R;

/**
 * Created by Deean on 4.3.2016..
 */
public class OsnovneInformacijeFragment extends android.support.v4.app.Fragment{

    private static  final String ARG_EXAMPLE = "this_is_a_constant";
    private String example_data;
    TextView imeLijeka,sastav,namjena,pakovanje,imeIAdresa;
    ImageView ivLijek;
    int [] poljeSlika = {R.drawable.lupocet_100,R.drawable.neofen_100,R.drawable.naklofen_100};


    public OsnovneInformacijeFragment(){


    }

    public static OsnovneInformacijeFragment newIstance (String example_argument){
        OsnovneInformacijeFragment osnovneInformacijeFragment = new OsnovneInformacijeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_EXAMPLE, example_argument);
        osnovneInformacijeFragment.setArguments(args);
        return osnovneInformacijeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        example_data = getArguments().getString(ARG_EXAMPLE);
        Log.i("Fragment created with", example_data);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_osnovne_informacije,container,false);

    }
}
