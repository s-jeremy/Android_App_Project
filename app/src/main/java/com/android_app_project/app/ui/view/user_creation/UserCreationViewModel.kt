package com.android_app_project.app.ui.view.user_creation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android_app_project.app.ui.domain.repository.SampleRemoteRepository
import com.android_app_project.app.ui.utils.mvvm.BaseViewModel
import com.android_app_project.app.ui.view.Failed
import com.android_app_project.app.ui.view.ViewModelState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class UserCreationViewModel(
    private val sampleRemoteRepository: SampleRemoteRepository
) : BaseViewModel() {
    val states = MutableLiveData<ViewModelState>()
    //Data Class de récupération des données de l'API de création user
    data class GetUserInformationResult(val status: Int) : ViewModelState()
    //Récupération des données provenant de l'API create user
    fun createUser(login: String,password: String,email: String,firstName: String, lastName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val status = sampleRemoteRepository.createUser(login,password,email,firstName,lastName)
                states.postValue(GetUserInformationResult(status))
            } catch (err: Exception) {
                states.postValue(Failed(err))
            }
        }
    }
}