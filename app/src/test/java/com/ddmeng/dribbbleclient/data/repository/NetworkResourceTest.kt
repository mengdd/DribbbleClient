package com.ddmeng.dribbbleclient.data.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import com.ddmeng.dribbbleclient.data.remote.ApiResponse
import com.ddmeng.dribbbleclient.data.valueobject.Resource
import com.ddmeng.dribbbleclient.data.valueobject.Status
import com.ddmeng.dribbbleclient.utils.ApiUtil
import com.ddmeng.dribbbleclient.utils.argumentCaptor
import com.ddmeng.dribbbleclient.utils.mock
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.verify
import retrofit2.Response

class NetworkResourceTest {
    private lateinit var networkResource: NetworkResource<Foo>
    private lateinit var handleCreateCall: () -> LiveData<ApiResponse<Foo>>

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun testFetchSuccessful() {
        val observer = mock<Observer<Resource<Foo>>>()
        val foo = Foo(1)
        handleCreateCall = { ApiUtil.successCall(foo) }

        networkResource = object : NetworkResource<Foo>() {
            override fun createCall(): LiveData<ApiResponse<Foo>> {
                return handleCreateCall()
            }
        }

        networkResource.asLiveData().observeForever(observer)

        verify(observer).onChanged(Resource.success(foo))
    }

    @Test
    fun testFetchEmpty() {
        val observer = mock<Observer<Resource<Foo>>>()

        val emptyRawResponse = Response.success(null).raw().newBuilder().code(204).build()
        val emptyResponse = ApiUtil.createCall(Response.success(null, emptyRawResponse))
        handleCreateCall = { emptyResponse as LiveData<ApiResponse<Foo>> }

        networkResource = object : NetworkResource<Foo>() {
            override fun createCall(): LiveData<ApiResponse<Foo>> {
                return handleCreateCall()
            }
        }

        val captor = argumentCaptor<Resource<Foo>>()

        networkResource.asLiveData().observeForever(observer)

        verify(observer).onChanged(captor.capture())

        assertEquals(captor.value.status, Status.ERROR)
        assertEquals(captor.value.message, "empty")
    }

    @Test
    fun testFetchError() {
        val observer = mock<Observer<Resource<Foo>>>()
        val body = ResponseBody.create(null, "error")
        handleCreateCall = { ApiUtil.createCall(Response.error<Foo>(400, body)) }

        networkResource = object : NetworkResource<Foo>() {
            override fun createCall(): LiveData<ApiResponse<Foo>> {
                return handleCreateCall()
            }
        }

        networkResource.asLiveData().observeForever(observer)

        val captor = argumentCaptor<Resource<Foo>>()

        verify(observer).onChanged(captor.capture())
        assertEquals(captor.value.status, Status.ERROR)
        assertEquals(captor.value.message, "error")
    }

    private data class Foo(var value: Int)
}
