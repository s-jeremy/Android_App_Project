package com.android_app_project.app.ui.domain.repository

import com.android_app_project.app.data.models.LoginResult
import com.android_app_project.app.data.remote.SampleRemoteDataSource
import kotlinx.coroutines.delay

interface SampleRemoteRepository {
    suspend fun ping(): String
    suspend fun getVersion(): String
    suspend fun login(login: String,password: String): Int
    suspend fun createUser(username: String,password: String,email: String,firstName: String, lastName: String): Int
}

class SampleRemoteRemoteRepositoryImpl(private val sampleRemoteDataSource: SampleRemoteDataSource) :
    SampleRemoteRepository {

    override suspend fun ping(): String {
        delay(1000L)
        val result = sampleRemoteDataSource.ping()
        return if(result.isSuccess()) {
            "Succeed Remote"
        } else {
            "Failed Remote"
        }
    }

    override suspend fun getVersion(): String {
        return sampleRemoteDataSource.restInfo().release
    }

    override suspend fun login(login: String,password: String): Int {
        return sampleRemoteDataSource.readUsers(login,password)
    }

    override suspend fun createUser(username: String,password: String,email: String,firstName: String, lastName: String): Int {
        return sampleRemoteDataSource.createUser(username,password,email,firstName,lastName)
    }
}

