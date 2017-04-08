package com.dtech.myapplication;

import android.app.Application;

/**
 * Created by aris on 07/04/17.
 */

public class MyApp extends Application {

    private static MyApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static synchronized MyApp getInstance() {
        return mInstance;
    }
}
