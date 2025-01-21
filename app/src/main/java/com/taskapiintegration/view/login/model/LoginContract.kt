package com.taskapiintegration.view.login.model

import com.taskapiintegration.view.login.data.LoginResponse

interface LoginContract {
    interface View{
        fun showErrorDialog(error: String, message: String)
        fun loginSuccess(loginResponse: LoginResponse)

    }
    interface Model{
        fun onLoginSuccess(loginResponse: LoginResponse)
        fun onResponseFailure(error: String, message: String)

    }
}