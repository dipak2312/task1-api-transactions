package com.taskapiintegration.view.login.viewmodel

import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.taskapiintegration.api.ApiService
import com.taskapiintegration.application.Application
import com.taskapiintegration.view.login.data.LoginRequest
import com.taskapiintegration.view.login.data.LoginResponse
import kotlinx.coroutines.launch

class LoginViewModel(private val app: android.app.Application) : AndroidViewModel(app) {

    private val _loginResult = MutableLiveData<Result<LoginResponse>>()
    val loginResult: LiveData<Result<LoginResponse>> get() = _loginResult

    private val api: ApiService = Application.retrofit.create(ApiService::class.java)

    fun login(userName: String, password: String) {
        if (userName.isNotEmpty() && password.isNotEmpty()) {
            val loginRequest = LoginRequest(userName, password)
            viewModelScope.launch {
                try {
                    val response = api.login(loginRequest)
                    _loginResult.postValue(Result.success(response))
                } catch (e: Exception) {
                    _loginResult.postValue(Result.failure(e))
                }
            }
        }
        else{
            Toast.makeText(app,"Enter user details",Toast.LENGTH_SHORT).show()
        }
    }
}
