package com.denisgithuku.notifications.viewmodels

import android.app.Application
import androidx.lifecycle.*

class MainViewModel(
    application: Application
) : AndroidViewModel(application){

    val input = MutableLiveData<String>()

    private val _timeInput = MutableLiveData<String>()
    val timeInput: LiveData<String> = _timeInput

}

class MainViewModelFactory(
    private var application: Application
): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}