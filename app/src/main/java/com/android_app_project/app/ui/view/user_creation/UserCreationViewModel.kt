package com.android_app_project.app.ui.view.user_creation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android_app_project.app.ui.domain.repository.SampleLocalRepository
import com.android_app_project.app.ui.domain.repository.SampleRemoteRepository
import com.android_app_project.app.ui.utils.mvvm.BaseViewModel
import com.android_app_project.app.ui.view.*
import com.android_app_project.app.ui.view.info.InfoViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class UserCreationViewModel(
    private val sampleRemoteRepository: SampleRemoteRepository
) : BaseViewModel() {
    val states = MutableLiveData<ViewModelState>()

    data class GetUserInformationResult(val firstName: String,
                                        val lastName: String,
                                        val username: String,
                                        val email: String,
                                        val password: String) : ViewModelState()

    fun getUserInformation() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                //val information = sampleRemoteRepository.getUserInformation()
                //states.postValue(InfoViewModel.GetUserInformationResult(information))
            } catch (err: Exception) {
                states.postValue(Failed(err))
            }
        }
    }
}