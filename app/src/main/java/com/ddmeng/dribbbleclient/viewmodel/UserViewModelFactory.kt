package com.ddmeng.dribbbleclient.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.ddmeng.dribbbleclient.data.repository.UserRepository
import com.ddmeng.dribbbleclient.utils.PreferencesUtils

class UserViewModelFactory(
    private val userRepository: UserRepository,
    private val preferencesUtils: PreferencesUtils
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(userRepository, preferencesUtils) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}