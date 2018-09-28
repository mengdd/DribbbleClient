package com.ddmeng.dribbbleclient.di

import com.ddmeng.dribbbleclient.testing.SingleFragmentActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class TestActivityModule {
    @ContributesAndroidInjector(modules = [TestFragmentModule::class])
    abstract fun contributeSingleActivity(): SingleFragmentActivity
}
