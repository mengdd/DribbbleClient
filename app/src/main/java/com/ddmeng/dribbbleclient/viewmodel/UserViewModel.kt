package com.ddmeng.dribbbleclient.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.ddmeng.dribbbleclient.data.model.User
import com.ddmeng.dribbbleclient.data.repository.UserRepository
import com.ddmeng.dribbbleclient.data.valueobject.Resource

class UserViewModel constructor(private val repository: UserRepository) : ViewModel() {

    fun getUserInfo(): LiveData<Resource<User>> {
        return repository.getUser()
    }
}