package com.ddmeng.dribbbleclient.di.module

import android.app.Application
import com.ddmeng.dribbbleclient.data.local.DribbbleClientDatabase
import com.ddmeng.dribbbleclient.data.local.UserDao
import com.ddmeng.dribbbleclient.data.remote.ServiceGenerator
import com.ddmeng.dribbbleclient.data.remote.UserService
import com.ddmeng.dribbbleclient.data.repository.UserRepository
import com.ddmeng.dribbbleclient.utils.PreferencesUtils
import com.ddmeng.dribbbleclient.viewmodel.UserViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideDatabase(app: Application): DribbbleClientDatabase {
        return DribbbleClientDatabase.getInstance(app)
    }

    @Singleton
    @Provides
    fun provideUserDao(dribbbleClientDatabase: DribbbleClientDatabase): UserDao {
        return dribbbleClientDatabase.userDao()
    }

    @Singleton
    @Provides
    fun provideUserService(serviceGenerator: ServiceGenerator): UserService {
        serviceGenerator.changeBaseUrl(ServiceGenerator.API_BASE_URL)
        return serviceGenerator.createService(UserService::class.java)
    }

    @Singleton
    @Provides
    fun provideUserViewModelFactory(userRepository: UserRepository, preferencesUtils: PreferencesUtils): UserViewModelFactory {
        return UserViewModelFactory(userRepository, preferencesUtils)
    }
}