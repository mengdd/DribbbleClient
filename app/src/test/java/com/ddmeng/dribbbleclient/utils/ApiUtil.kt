package com.ddmeng.dribbbleclient.utils

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.ddmeng.dribbbleclient.data.remote.ApiResponse
import retrofit2.Response

object ApiUtil {
    fun <T : Any> successCall(data: T) = createCall(Response.success(data))

    fun <T : Any> createCall(response: Response<T>) = MutableLiveData<ApiResponse<T>>().apply {
        value = ApiResponse.create(response)
    } as LiveData<ApiResponse<T>>
}