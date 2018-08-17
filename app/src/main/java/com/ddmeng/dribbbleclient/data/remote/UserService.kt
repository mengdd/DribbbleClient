package com.ddmeng.dribbbleclient.data.remote

import com.ddmeng.dribbbleclient.data.model.User
import io.reactivex.Single
import retrofit2.http.GET

interface UserService {
    @GET("user")
    fun getUser(): Single<User>
}