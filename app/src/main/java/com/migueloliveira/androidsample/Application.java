package com.migueloliveira.androidsample;

import com.facebook.stetho.Stetho;

/**
 * Created by migueloliveira on 20/10/2016.
 */

public class Application extends android.app.Application {
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
