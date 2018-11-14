package com.ddmeng.dribbbleclient.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.ddmeng.dribbbleclient.data.repository.ShotRepository

class ShotViewModelFactory(
    private val shotRepository: ShotRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShotsViewModel::class.java)) {
            return ShotsViewModel(shotRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
