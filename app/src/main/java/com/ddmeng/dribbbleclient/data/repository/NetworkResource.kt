package com.ddmeng.dribbbleclient.data.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import com.ddmeng.dribbbleclient.data.remote.ApiEmptyResponse
import com.ddmeng.dribbbleclient.data.remote.ApiErrorResponse
import com.ddmeng.dribbbleclient.data.remote.ApiResponse
import com.ddmeng.dribbbleclient.data.remote.ApiSuccessResponse
import com.ddmeng.dribbbleclient.data.valueobject.Resource

abstract class NetworkResource<T> {
    private var result = MediatorLiveData<Resource<T>>()

    init {
        @Suppress("LeakingThis")
        val apiCall = createCall()
        result.addSource(apiCall) {
            result.removeSource(apiCall)
            when (it) {
                is ApiSuccessResponse -> {
                    setValue(Resource.success(it.body))
                }
                is ApiEmptyResponse -> {
                    setValue(Resource.error("empty", null))
                }
                is ApiErrorResponse -> {
                    setValue(Resource.error(it.errorMessage, null))
                }
            }
        }
    }

    abstract fun createCall(): LiveData<ApiResponse<T>>

    fun asLiveData() = result as LiveData<Resource<T>>

    private fun setValue(newValue: Resource<T>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }
}
