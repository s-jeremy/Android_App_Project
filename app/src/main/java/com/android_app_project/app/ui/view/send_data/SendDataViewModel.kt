package com.android_app_project.app.ui.view.send_data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android_app_project.app.ui.domain.repository.SampleRemoteRepository
import com.android_app_project.app.ui.utils.mvvm.BaseViewModel
import com.android_app_project.app.ui.view.Failed
import com.android_app_project.app.ui.view.ViewModelState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SendDataViewModel(
    private val sampleRemoteRepository: SampleRemoteRepository
) : BaseViewModel() {
    val states = MutableLiveData<ViewModelState>()

    //Data Class de récupération du résultat de sendData
    data class SendDataResult(val status: Int) : ViewModelState()

    //Récupération des résultat de l'appel de l'API d'envoi de données
    fun sendData(id_user: Int, luminosite : String, batterie : String, pression : String, temperature : String, gps : String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val status = sampleRemoteRepository.sendData(id_user, luminosite, batterie, pression, temperature, gps)
                states.postValue(SendDataResult(status))
            } catch (err: Exception) {
                states.postValue(Failed(err))
            }
        }
    }
}