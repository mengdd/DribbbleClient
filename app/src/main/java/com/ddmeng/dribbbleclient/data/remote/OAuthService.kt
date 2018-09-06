package com.ddmeng.dribbbleclient.data.remote

import com.ddmeng.dribbbleclient.data.model.OAuthToken
import io.reactivex.Single
import retrofit2.http.POST
import retrofit2.http.Query

interface OAuthService {
    @POST("token")
    fun getToken(
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("code") code: String,
        @Query("redirect_url") redirectUrl: String
    ): Single<OAuthToken>
}