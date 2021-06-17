package com.android_app_project.app.ui.di

import com.android_app_project.app.ui.domain.repository.SampleRemoteRemoteRepositoryImpl
import com.android_app_project.app.ui.domain.repository.SampleRemoteRepository
import com.android_app_project.app.ui.view.collected_data.CollectedDataViewModel
import com.android_app_project.app.ui.view.login.LoginViewModel
import com.android_app_project.app.ui.view.main.MainViewModel
import com.android_app_project.app.ui.view.send_data.SendDataViewModel
import com.android_app_project.app.ui.view.user_creation.UserCreationViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Inject dependencies for the MainViewModel (the only UI in this boilerplate)
    viewModel { MainViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { UserCreationViewModel(get()) }
    viewModel { CollectedDataViewModel(get()) }
    viewModel { SendDataViewModel(get()) }

    // Sample Remote Data Repository
    single<SampleRemoteRepository>(createdAtStart = true) { SampleRemoteRemoteRepositoryImpl(get()) }


}

val moduleApp = listOf(appModule, remoteDataSourceModule)