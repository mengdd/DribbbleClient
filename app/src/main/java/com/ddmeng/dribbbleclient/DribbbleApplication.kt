package com.ddmeng.dribbbleclient

import android.app.Application

import com.facebook.stetho.Stetho

class DribbbleApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)
    }
}
