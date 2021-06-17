package com.android_app_project.app.ui.view.main

import androidx.lifecycle.MutableLiveData
import com.android_app_project.app.ui.domain.repository.SampleRemoteRepository
import com.android_app_project.app.ui.utils.mvvm.BaseViewModel
import com.android_app_project.app.ui.view.ViewModelState


class MainViewModel(
    private val sampleRemoteRepository: SampleRemoteRepository
) : BaseViewModel() {
    val states = MutableLiveData<ViewModelState>()

    data class CallResult(val data: String) : ViewModelState()
}