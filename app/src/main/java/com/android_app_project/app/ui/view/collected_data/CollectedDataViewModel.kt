package com.android_app_project.app.ui.view.collected_data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android_app_project.app.ui.domain.repository.SampleRemoteRepository
import com.android_app_project.app.ui.utils.mvvm.BaseViewModel
import com.android_app_project.app.ui.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception


class CollectedDataViewModel(
    private val sampleRemoteRepository: SampleRemoteRepository
) : BaseViewModel() {
    val states = MutableLiveData<ViewModelState>()

    data class CollecteDataResult(val status: String) : ViewModelState()

    fun collecteData(id_user: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val status = sampleRemoteRepository.collecteData(id_user)
                states.postValue(CollecteDataResult(status))
            } catch (err: Exception) {
                states.postValue(Failed(err))
            }
        }
    }
}