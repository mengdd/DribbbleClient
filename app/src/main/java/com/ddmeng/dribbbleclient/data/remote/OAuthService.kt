package com.ddmeng.dribbbleclient.data.remote

import android.arch.lifecycle.LiveData
import com.ddmeng.dribbbleclient.data.model.OAuthToken
import retrofit2.http.POST
import retrofit2.http.Query

interface OAuthService {
    @POST("token")
    fun getToken(
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("code") code: String,
        @Query("redirect_url") redirectUrl: String
    ): LiveData<ApiResponse<OAuthToken>>
}
