package com.ddmeng.dribbbleclient.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.ddmeng.dribbbleclient.data.model.Shot
import com.ddmeng.dribbbleclient.data.repository.ShotRepository
import com.ddmeng.dribbbleclient.data.valueobject.Resource

class ShotsViewModel constructor(
    private val repository: ShotRepository
) : ViewModel() {

    private val forceFetch = MutableLiveData<Boolean>()
    val shots: LiveData<Resource<List<Shot>>> = Transformations.switchMap(forceFetch) { forceFetch ->
        repository.loadShots(forceFetch)
    }

    fun fetch(force: Boolean) {
        forceFetch.value = force
    }
}