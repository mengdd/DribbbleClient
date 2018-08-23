package com.ddmeng.dribbbleclient.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.ddmeng.dribbbleclient.data.model.User
import com.ddmeng.dribbbleclient.data.repository.UserRepository
import com.ddmeng.dribbbleclient.data.valueobject.Resource
import com.ddmeng.dribbbleclient.utils.PreferencesUtils

class UserViewModel constructor(private val repository: UserRepository,
                                private val preferencesUtils: PreferencesUtils) : ViewModel() {

    fun getUserInfo(): LiveData<Resource<User>> {
        return repository.getUser()
    }

    fun deleteUser(user: User) {
        repository.deleteUser(user)
        preferencesUtils.saveUserLoggedIn(false)
    }
}