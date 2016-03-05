package com.example.deean.medix.tabs_fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.deean.medix.R;

/**
 * Created by Deean on 4.3.2016..
 */
public class MjereOprezaFragment extends android.support.v4.app.Fragment{

    private static  final String ARG_EXAMPLE = "this_is_a_constant";
    private String example_data;
    TextView proba;


    public MjereOprezaFragment(){


    }

    public static MjereOprezaFragment newIstance (String example_argument){
        MjereOprezaFragment osnovneInformacijeFragment = new MjereOprezaFragment();
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
        //proba = (TextView) proba.findViewById(R.id.textView);
        //proba.setText("LALALALA");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LayoutInflater lf = getActivity().getLayoutInflater();
        View view = lf.inflate(R.layout.fragment_mjere_opreza, container, false);
        TextView text = (TextView) view.findViewById(R.id.textView);
        text.setText("test");
        return view;
    }
}
