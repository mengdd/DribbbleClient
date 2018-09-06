package com.ddmeng.dribbbleclient.di.module

import com.ddmeng.dribbbleclient.features.auth.OAuthFragment
import com.ddmeng.dribbbleclient.features.home.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeOAuthFragment(): OAuthFragment
}
