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
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
        private val appExecutors: AppExecutors,
        private val userDao: UserDao,
        private val userService: UserService) {

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