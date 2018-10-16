package com.ddmeng.dribbbleclient.di

import android.app.Application
import com.ddmeng.dribbbleclient.TestApp
import com.ddmeng.dribbbleclient.di.module.ApiModule
import com.ddmeng.dribbbleclient.di.module.AppModule
import com.ddmeng.dribbbleclient.di.module.MainActivityModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AndroidSupportInjectionModule::class,
    AppModule::class,
    MainActivityModule::class,
    ApiModule::class,
    TestActivityModule::class])
interface TestComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): TestComponent.Builder

        fun build(): TestComponent
    }

    fun inject(app: TestApp)
}
