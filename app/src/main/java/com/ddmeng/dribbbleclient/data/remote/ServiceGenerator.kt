package com.ddmeng.dribbbleclient.data.remote

import com.ddmeng.dribbbleclient.utils.PreferencesUtils
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class ServiceGenerator constructor(preferencesUtils: PreferencesUtils) {

    companion object {
        const val OAUTH_BASE_URL = "https://dribbble.com/oauth/"
        const val API_BASE_URL = "https://api.dribbble.com/v2/"
    }

    private val httpClientBuilder = OkHttpClient.Builder().addInterceptor(AuthInterceptor(preferencesUtils))

    private var retrofitBuilder = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

    fun changeBaseUrl(newBaseUrl: String) {
        retrofitBuilder = Retrofit.Builder()
                .baseUrl(newBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    }

    fun <S> createService(serviceClass: Class<S>): S {
        val client = httpClientBuilder.build()
        val retrofit = retrofitBuilder.client(client).build()
        return retrofit.create(serviceClass)
    }
}

