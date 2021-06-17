package com.android_app_project.app.ui.view.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android_app_project.app.ui.domain.repository.SampleRemoteRepository
import com.android_app_project.app.ui.utils.mvvm.BaseViewModel
import com.android_app_project.app.ui.view.Failed
import com.android_app_project.app.ui.view.ViewModelState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    private val sampleRemoteRepository: SampleRemoteRepository
) : BaseViewModel() {
    val states = MutableLiveData<ViewModelState>()

    // Data Class du résultat du login
    data class LoginResult(val status: Int) : ViewModelState()

    //Récupération du résultat de l'API
    fun login( login: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val status = sampleRemoteRepository.login(login,password)
                states.postValue(LoginResult(status))
            } catch (err: Exception) {
                states.postValue(Failed(err))
            }
        }
    }
}