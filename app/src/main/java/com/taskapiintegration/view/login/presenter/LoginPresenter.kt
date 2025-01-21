package com.taskapiintegration.view.login.presenter

import com.taskapiintegration.databinding.ActivityLoginBinding
import com.taskapiintegration.view.login.data.LoginRequest
import com.taskapiintegration.view.login.data.LoginResponse
import com.taskapiintegration.view.login.interactor.LoginInteractor
import com.taskapiintegration.view.login.model.LoginContract

class LoginPresenter(private val view: LoginContract.View, private val interactor: LoginInteractor):LoginContract.Model {

    fun login(binding: ActivityLoginBinding) {
            val userName = binding.userName.text.toString()
            val password = binding.password.text.toString()

            if (userName.isNotEmpty() && password.isNotEmpty()) {
                val loginRequest = LoginRequest(userName,password)
                interactor.login(this, loginRequest)
            }
    }

    override fun onLoginSuccess(loginResponse: LoginResponse) {
        view.loginSuccess(loginResponse)
    }

    override fun onResponseFailure(error: String, message: String) {
        view.showErrorDialog(error,message)
    }
}