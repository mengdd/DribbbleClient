package com.ddmeng.dribbbleclient.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object ServiceGenerator {

    val OAUTH_BASE_URL = "https://dribbble.com/oauth/"

    private val httpClientBuilder = OkHttpClient.Builder()

    private val retrofitBuilder = Retrofit.Builder()
            .baseUrl(OAUTH_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

    fun <S> createService(serviceClass: Class<S>): S {
        val client = httpClientBuilder.build()
        val retrofit = retrofitBuilder.client(client).build()
        return retrofit.create(serviceClass)
    }
}