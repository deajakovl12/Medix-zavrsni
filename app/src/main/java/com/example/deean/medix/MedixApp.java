package com.example.deean.medix;

import android.app.Application;
import android.content.Context;

/**
 * Created by dean on 24.08.16..
 */
public class MedixApp extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        MedixApp.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MedixApp.context;
    }
}

