package com.ddmeng.dribbbleclient.data.repository

import android.arch.lifecycle.LiveData
import com.ddmeng.dribbbleclient.AppExecutors
import com.ddmeng.dribbbleclient.data.local.ShotDao
import com.ddmeng.dribbbleclient.data.model.Shot
import com.ddmeng.dribbbleclient.data.remote.ApiResponse
import com.ddmeng.dribbbleclient.data.remote.ShotService
import com.ddmeng.dribbbleclient.data.valueobject.Resource
import javax.inject.Inject

class ShotRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val shotDao: ShotDao,
    private val shotService: ShotService
) {

    fun loadShots(forceFetch: Boolean): LiveData<Resource<List<Shot>>> {
        return object : NetworkBoundResource<List<Shot>, List<Shot>>(appExecutors) {
            override fun saveCallResult(item: List<Shot>) {
                shotDao.insertShots(item)
            }

            override fun shouldFetch(data: List<Shot>?): Boolean {
                return forceFetch || data == null || data.isEmpty()
            }

            override fun loadFromDb(): LiveData<List<Shot>> {
                return shotDao.getAllShots()
            }

            override fun createCall(): LiveData<ApiResponse<List<Shot>>> {
                return shotService.getUserShots()
            }
        }.asLiveData()
    }
}