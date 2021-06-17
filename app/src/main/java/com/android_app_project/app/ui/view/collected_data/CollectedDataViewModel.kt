package com.android_app_project.app.ui.view.collected_data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android_app_project.app.ui.domain.repository.SampleRemoteRepository
import com.android_app_project.app.ui.utils.mvvm.BaseViewModel
import com.android_app_project.app.ui.view.Failed
import com.android_app_project.app.ui.view.ViewModelState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CollectedDataViewModel(
    private val sampleRemoteRepository: SampleRemoteRepository
) : BaseViewModel() {
    val states = MutableLiveData<ViewModelState>()

    //data class de récupération du résultat
    data class GetCollectDataResult(val status: Array<String>) : ViewModelState()

    //Récupération du résultat provenant de l'API
    fun collecteData(id_user: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val status = sampleRemoteRepository.collecteData(id_user)
                states.postValue(GetCollectDataResult(status))
            } catch (err: Exception) {
                states.postValue(Failed(err))
            }
        }
    }
}