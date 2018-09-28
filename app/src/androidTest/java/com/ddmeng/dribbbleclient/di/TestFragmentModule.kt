package com.ddmeng.dribbbleclient.di

import com.ddmeng.dribbbleclient.OAuthFragmentTest
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class TestFragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeTestOAuthFragment(): OAuthFragmentTest.TestOAuthFragment
}
