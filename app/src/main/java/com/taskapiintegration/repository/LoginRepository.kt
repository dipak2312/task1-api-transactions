package com.taskapiintegration.repository

import com.taskapiintegration.api.ApiService
import com.taskapiintegration.application.Application
import com.taskapiintegration.view.login.data.LoginRequest
import com.taskapiintegration.view.login.data.LoginResponse

class LoginRepository {
    private val retrofit = Application.retrofit.create(ApiService::class.java)

    suspend fun login(request: LoginRequest): LoginResponse {
        return retrofit.login(request)
    }
}


