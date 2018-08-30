package com.ddmeng.dribbbleclient.di.module

import android.webkit.CookieManager
import dagger.Module
import dagger.Provides

@Module
class AndroidModule {

    @Provides
    fun provideCookieManager(): CookieManager {
        return CookieManager.getInstance()
    }
}