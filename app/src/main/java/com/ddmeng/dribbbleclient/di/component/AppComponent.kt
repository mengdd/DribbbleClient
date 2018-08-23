package com.ddmeng.dribbbleclient.di.component

import android.app.Application
import com.ddmeng.dribbbleclient.DribbbleApplication
import com.ddmeng.dribbbleclient.di.module.AppModule
import com.ddmeng.dribbbleclient.di.module.MainActivityModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AndroidInjectionModule::class,
            AndroidSupportInjectionModule::class,
            AppModule::class,
            MainActivityModule::class
        ]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): AppComponent.Builder

        fun build(): AppComponent
    }

    fun inject(githubApp: DribbbleApplication)
}