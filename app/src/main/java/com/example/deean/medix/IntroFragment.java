package com.example.deean.medix;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.deean.medix.databinding.IntroFragmentBinding;
import com.example.deean.medix.doktorovo.IntroActivityDoctor;
import com.example.deean.medix.doktorovo.Prijava;
import com.squareup.picasso.Picasso;

/**
 * Created by dean on 24.08.16..
 */
public class IntroFragment extends android.support.v4.app.Fragment {

    private static final String ARG_EXAMPLE = "this_is_a_constant";
    private String data;

    IntroFragmentBinding bind;



    public IntroFragment() {
    }

    public static IntroFragment newIstance(String example_argument) {
        IntroFragment introFragment = new IntroFragment();
        Bundle args = new Bundle();
        args.putString(ARG_EXAMPLE, example_argument);
        introFragment.setArguments(args);
        return introFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        bind = DataBindingUtil.inflate(inflater, R.layout.intro_fragment, container, false);
        data = getArguments().getString(ARG_EXAMPLE);

        switch (data){
            case IntroActivityDoctor.FIRST:
                setIntroScreen(R.color.background_color,R.drawable.first,R.string.first,R.string.first_text); // background prvo. sve to namjestit
                break;
            case IntroActivityDoctor.SECOND:
                setIntroScreen(R.color.background_color,R.drawable.second,R.string.second,R.string.second_text);
                break;
            case IntroActivityDoctor.THIRD:
                setIntroScreen(R.color.background_color,R.drawable.third,R.string.third,R.string.third_text);
                break;
            case IntroActivityDoctor.LAST:
                setIntroScreen(R.color.background_color,R.drawable.last,R.string.last,R.string.last_text);
                bind.next.setVisibility(View.VISIBLE);
                bind.next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferencesHelper.setShowIntroScreen(true);
                            Intent i = new Intent(getContext(), Prijava.class);
                            startActivity(i);
//                            Intent i = new Intent(getContext(), OnBoardActivity.class);
//                            startActivity(i);
                    }
                });
                break;
        }
        return bind.getRoot();
    }
    private void setIntroScreen(int background,int screen, int title, int text){
        Picasso.with(getContext())
                .load(background)
                .fit()
                .into(bind.background);
        bind.introScreen.setImageResource(screen);
        bind.titleIntro.setText(getString(title));
        bind.textIntro.setText(getString(text));
    }
}
