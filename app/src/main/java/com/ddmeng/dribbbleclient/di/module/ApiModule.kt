package com.ddmeng.dribbbleclient.di.module

import com.ddmeng.dribbbleclient.data.remote.AuthInterceptor
import com.ddmeng.dribbbleclient.data.remote.OAuthService
import com.ddmeng.dribbbleclient.data.remote.ShotService
import com.ddmeng.dribbbleclient.data.remote.UserService
import com.ddmeng.dribbbleclient.utils.LiveDataCallAdapterFactory
import com.ddmeng.dribbbleclient.utils.PreferencesUtils
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApiModule {
    companion object {
        const val OAUTH_BASE_URL = "https://dribbble.com/oauth/"
        const val API_BASE_URL = "https://api.dribbble.com/v2/"
    }

    @Singleton
    @Provides
    @Named("shotEndpoint")
    fun shotEndpoint() = API_BASE_URL

    @Singleton
    @Provides
    @Named("authEndpoint")
    fun authEndpoint() = OAUTH_BASE_URL

    @Singleton
    @Provides
    fun interceptor(preferencesUtils: PreferencesUtils) = AuthInterceptor(preferencesUtils)

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
        return httpLoggingInterceptor
    }

    @Singleton
    @Provides
    fun provideOkHttpBuilder(): OkHttpClient.Builder {
        val okHttpBuilder = OkHttpClient.Builder()
        return okHttpBuilder.apply {
            readTimeout(15.toLong(), TimeUnit.SECONDS)
            connectTimeout(15.toLong(), TimeUnit.SECONDS)
        }
    }

    @Singleton
    @Provides
    @Named("shotRetrofit")
    fun provideShotRetrofit(
        retrofitBuilder: Retrofit.Builder,
        okHttpClientBuilder: OkHttpClient.Builder,
        interceptor: AuthInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        @Named("shotEndpoint") baseUrl: String
    ): Retrofit {
        val client = okHttpClientBuilder
                .addInterceptor(interceptor)
                .addInterceptor(httpLoggingInterceptor)
                .build()
        return retrofitBuilder
                .client(client)
                .baseUrl(baseUrl)
                .build()
    }

    @Singleton
    @Provides
    @Named("authRetrofit")
    fun provideUserRetrofit(
        retrofitBuilder: Retrofit.Builder,
        okHttpClientBuilder: OkHttpClient.Builder,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        @Named("authEndpoint") baseUrl: String
    ): Retrofit {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        val okHttpClient = okHttpClientBuilder
                .addInterceptor(httpLoggingInterceptor).build()
        return retrofitBuilder
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
    }

    @Singleton
    @Provides
    fun provideUserApiService(@Named("shotRetrofit") retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Singleton
    @Provides
    fun provideShotService(@Named("shotRetrofit") retrofit: Retrofit): ShotService {
        return retrofit.create(ShotService::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthApiService(@Named("authRetrofit") retrofit: Retrofit): OAuthService {
        return retrofit.create(OAuthService::class.java)
    }
}
