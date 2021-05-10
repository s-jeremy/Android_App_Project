package com.android_app_project.app.view.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android_app_project.app.domain.repository.SampleLocalRepository
import com.android_app_project.app.domain.repository.SampleRemoteRepository
import com.android_app_project.app.utils.mvvm.BaseViewModel
import com.android_app_project.app.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel(
    private val sampleRemoteRepository: SampleRemoteRepository,
    private val sampleLocalRepository: SampleLocalRepository
) : BaseViewModel() {
    val states = MutableLiveData<ViewModelState>()

    fun doRemoteTestAction() {
        states.postValue(Loading)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = sampleRemoteRepository.ping()
                states.postValue(CallResult(result))
            } catch (err: Exception) {
                states.postValue(Failed(err))
            }
        }
    }

    fun doLocalTestAction() {
        states.postValue(Loading)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = sampleLocalRepository.ping()
                states.postValue(CallResult(result))
            } catch (err: Exception) {
                states.postValue(Failed(err))
            }
        }
    }

    data class CallResult(val data: String) : ViewModelState()
}