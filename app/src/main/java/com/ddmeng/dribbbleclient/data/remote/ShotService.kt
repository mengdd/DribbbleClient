package com.ddmeng.dribbbleclient.data.remote

import android.arch.lifecycle.LiveData
import com.ddmeng.dribbbleclient.data.model.Shot
import retrofit2.http.GET

interface ShotService {
    @GET("user/shots")
    fun getUserShots(): LiveData<ApiResponse<List<Shot>>>
}