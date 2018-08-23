package com.ddmeng.dribbbleclient

import android.app.Activity
import android.app.Application
import com.ddmeng.dribbbleclient.di.AppInjector
import com.facebook.stetho.Stetho
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class DribbbleApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)
        AppInjector.init(this)
    }

    override fun activityInjector() = dispatchingAndroidInjector
}
