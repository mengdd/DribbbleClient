package com.ddmeng.dribbbleclient.data.remote

import android.arch.lifecycle.LiveData
import com.ddmeng.dribbbleclient.data.model.Shot
import retrofit2.http.GET

interface ShotService {
    @GET("user/shots")
    fun getUserShots(): LiveData<ApiResponse<List<Shot>>>

    @GET("popular_shots")
    fun getPopularShots(): LiveData<ApiResponse<List<Shot>>> // will get 403,
    // Note: This is available only to select applications with our approval.
}