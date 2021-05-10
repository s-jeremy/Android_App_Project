package com.android_app_project.app.view.info

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android_app_project.app.domain.repository.SampleRemoteRepository
import com.android_app_project.app.utils.mvvm.BaseViewModel
import com.android_app_project.app.view.Failed
import com.android_app_project.app.view.ViewModelState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class InfoViewModel(
    private val sampleRemoteRepository: SampleRemoteRepository
) : BaseViewModel() {
    val states = MutableLiveData<ViewModelState>()

    data class GetVersionResult(val version: String) : ViewModelState()

    fun getVersion() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val version = sampleRemoteRepository.getVersion()
                states.postValue(GetVersionResult(version))
            } catch (err: Exception) {
                states.postValue(Failed(err))
            }
        }
    }
}