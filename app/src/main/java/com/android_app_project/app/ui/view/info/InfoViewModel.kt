package com.android_app_project.app.ui.view.info

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android_app_project.app.ui.domain.repository.SampleRemoteRepository
import com.android_app_project.app.ui.utils.mvvm.BaseViewModel
import com.android_app_project.app.ui.view.Failed
import com.android_app_project.app.ui.view.ViewModelState
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