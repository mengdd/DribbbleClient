package com.ddmeng.dribbbleclient.data.remote

import com.ddmeng.dribbbleclient.utils.PreferencesUtils
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor constructor(private val preferencesUtils: PreferencesUtils) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val userToken = preferencesUtils.userToken
        val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $userToken").build()
        return chain.proceed(request)
    }
}