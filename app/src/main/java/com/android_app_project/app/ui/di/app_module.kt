package com.android_app_project.app.ui.di

import com.android_app_project.app.ui.domain.repository.SampleLocalRepository
import com.android_app_project.app.ui.domain.repository.SampleLocalRepositoryImpl
import com.android_app_project.app.ui.domain.repository.SampleRemoteRepository
import com.android_app_project.app.ui.domain.repository.SampleRemoteRemoteRepositoryImpl
import com.android_app_project.app.ui.view.collected_data.CollectedDataViewModel
import com.android_app_project.app.ui.view.info.InfoViewModel
import com.android_app_project.app.ui.view.login.LoginViewModel
import com.android_app_project.app.ui.view.main.MainViewModel
import com.android_app_project.app.ui.view.send_data.SendDataViewModel
import com.android_app_project.app.ui.view.user_creation.UserCreationViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Inject dependencies for the MainViewModel (the only UI in this boilerplate)
    viewModel { MainViewModel(get(), get()) }
    viewModel { InfoViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { UserCreationViewModel(get()) }
    viewModel { CollectedDataViewModel(get()) }
    viewModel { SendDataViewModel(get()) }

    // Sample Remote Data Repository
    single<SampleRemoteRepository>(createdAtStart = true) { SampleRemoteRemoteRepositoryImpl(get()) }

    // Sample Local Data Repository
    single<SampleLocalRepository>(createdAtStart = true) { SampleLocalRepositoryImpl() }

}

val moduleApp = listOf(appModule, remoteDataSourceModule)