package com.example.deean.medix;

import android.content.Context;

/**
 * Created by dean on 24.08.16..
 */
public class SharedPreferencesHelper {
    private static final String INTRO_SCREENS_KEY = "intro_screens";
    private static final String INTRO_SCREENS_KEY_P = "intro_screens_p";
    public static final String PREFERENCE_NAME = "shared_shared_preferences";

    public static boolean getShowIntroScreen(){
        return MedixApp.getAppContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                .getBoolean(INTRO_SCREENS_KEY,false);
    }

    public static void setShowIntroScreen(boolean hideIntroScreen){
        MedixApp.getAppContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                .edit()
                .putBoolean(INTRO_SCREENS_KEY, hideIntroScreen)
                .commit();
    }
    public static boolean getShowIntroScreenP(){
        return MedixApp.getAppContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                .getBoolean(INTRO_SCREENS_KEY_P,false);
    }

    public static void setShowIntroScreenP(boolean hideIntroScreen){
        MedixApp.getAppContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                .edit()
                .putBoolean(INTRO_SCREENS_KEY_P, hideIntroScreen)
                .commit();
    }
}