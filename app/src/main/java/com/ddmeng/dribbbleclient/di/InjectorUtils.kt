package com.ddmeng.dribbbleclient.di

import android.content.Context
import com.ddmeng.dribbbleclient.AppExecutors
import com.ddmeng.dribbbleclient.data.local.DribbbleClientDatabase
import com.ddmeng.dribbbleclient.data.local.UserDao
import com.ddmeng.dribbbleclient.data.remote.ServiceGenerator
import com.ddmeng.dribbbleclient.data.remote.UserService
import com.ddmeng.dribbbleclient.data.repository.UserRepository
import com.ddmeng.dribbbleclient.utils.PreferencesUtils
import com.ddmeng.dribbbleclient.viewmodel.UserViewModelFactory

object InjectorUtils { // DI implemented manually

    fun provideAppExecutors(): AppExecutors {
        return AppExecutors()
    }

    fun provideUserDao(context: Context): UserDao {
        return DribbbleClientDatabase.getInstance(context).userDao()
    }

    fun provideUserService(context: Context): UserService {
        return provideServiceGenerator(context).createService(UserService::class.java)
    }

    fun provideServiceGenerator(context: Context): ServiceGenerator {
        return ServiceGenerator(providePreferencesUtils(context.applicationContext))
    }

    fun providePreferencesUtils(context: Context): PreferencesUtils {
        return PreferencesUtils(context)
    }

    fun provideUserRepository(context: Context): UserRepository {
        return UserRepository.getInstance(provideAppExecutors(), provideUserDao(context), provideUserService(context))
    }

    fun provideUserViewModelFactory(context: Context): UserViewModelFactory {
        return UserViewModelFactory(provideUserRepository(context), providePreferencesUtils(context))
    }
}