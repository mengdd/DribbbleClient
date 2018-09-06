package com.ddmeng.dribbbleclient.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.ddmeng.dribbbleclient.data.model.User
import com.ddmeng.dribbbleclient.data.repository.UserRepository
import com.ddmeng.dribbbleclient.data.valueobject.Resource
import com.ddmeng.dribbbleclient.utils.PreferencesUtils

class UserViewModel constructor(
    private val repository: UserRepository,
    private val preferencesUtils: PreferencesUtils
) : ViewModel() {
    private val forceFetch = MutableLiveData<Boolean>()
    val user: LiveData<Resource<User>> = Transformations.switchMap(forceFetch) {
        repository.getUser(it)
    }

    fun fetch(force: Boolean) {
        forceFetch.value = force
    }

    fun deleteUser(user: User) {
        repository.deleteUser(user)
        preferencesUtils.saveUserLoggedIn(false)
        preferencesUtils.deleteToken()
    }
}
