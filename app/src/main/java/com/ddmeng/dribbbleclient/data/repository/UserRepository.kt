package com.ddmeng.dribbbleclient.data.repository

import android.arch.lifecycle.LiveData
import com.ddmeng.dribbbleclient.AppExecutors
import com.ddmeng.dribbbleclient.data.local.UserDao
import com.ddmeng.dribbbleclient.data.model.User
import com.ddmeng.dribbbleclient.data.remote.UserService
import com.ddmeng.dribbbleclient.data.valueobject.Resource
import com.ddmeng.dribbbleclient.utils.LogUtils
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers

class UserRepository private constructor(
        private val appExecutors: AppExecutors,
        private val userDao: UserDao,
        private val userService: UserService) {
    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(appExecutors: AppExecutors, userDao: UserDao, userService: UserService) =
                instance ?: synchronized(this) {
                    instance
                            ?: UserRepository(appExecutors, userDao, userService).also { instance = it }
                }
    }

    fun getUser(forceRefresh: Boolean): LiveData<Resource<User>> {
        return object : NetworkBoundResource<User, User>(appExecutors) {
            override fun saveCallResult(item: User) {
                userDao.insert(item)
            }

            override fun shouldFetch(data: User?) = (forceRefresh || data == null)

            override fun loadFromDb() = userDao.query()

            override fun createCall() = userService.getUser()
        }.asLiveData()
    }

    fun deleteUser(user: User) {
        Completable.fromAction {
            LogUtils.d("delete user: " + user.name)
            userDao.delete(user)
        }.subscribeOn(Schedulers.io()).subscribe()
    }
}