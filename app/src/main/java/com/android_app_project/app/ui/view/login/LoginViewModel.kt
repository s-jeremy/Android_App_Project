package com.android_app_project.app.ui.view.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android_app_project.app.ui.domain.repository.SampleRemoteRepository
import com.android_app_project.app.ui.utils.mvvm.BaseViewModel
import com.android_app_project.app.ui.view.Failed
import com.android_app_project.app.ui.view.ViewModelState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class LoginViewModel(
    private val sampleRemoteRepository: SampleRemoteRepository
) : BaseViewModel() {
    val states = MutableLiveData<ViewModelState>()

    data class LoginResult(val status: Int) : ViewModelState()

    fun login() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val status = sampleRemoteRepository.login("test","test")
                states.postValue(LoginResult(status))
            } catch (err: Exception) {
                states.postValue(Failed(err))
            }
        }
    }
}