package com.ddmeng.dribbbleclient;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class DribbbleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initializeWithDefaults(this);
    }
}
