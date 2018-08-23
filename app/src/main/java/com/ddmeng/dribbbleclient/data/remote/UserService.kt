package com.ddmeng.dribbbleclient.data.remote

import android.arch.lifecycle.LiveData
import com.ddmeng.dribbbleclient.data.model.User
import retrofit2.http.GET

interface UserService {
    @GET("user")
    fun getUser(): LiveData<ApiResponse<User>>
}