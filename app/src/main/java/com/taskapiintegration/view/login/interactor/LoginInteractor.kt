package com.taskapiintegration.view.login.interactor

import com.google.gson.Gson
import com.taskapiintegration.api.ApiService
import com.taskapiintegration.application.Application
import com.taskapiintegration.constants.Constants
import com.taskapiintegration.view.login.data.ErrorResponse
import com.taskapiintegration.view.login.data.LoginRequest
import com.taskapiintegration.view.login.data.LoginResponse
import com.taskapiintegration.view.login.model.LoginContract
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class LoginInteractor {
    fun login(listener: LoginContract.Model, loginRequest: LoginRequest) {
        try {
            val retrofit = Application.retrofit.create(ApiService::class.java)
            val response: Single<LoginResponse> = retrofit.login(loginRequest)
            response.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(object:
                DisposableSingleObserver<LoginResponse>(){
                override fun onSuccess(loginResponse: LoginResponse) {
                    if (loginResponse != null){
                        listener.onLoginSuccess(loginResponse)
                    }

                }

                override fun onError(e: Throwable) {
                    try {
                        val exception: HttpException = e as HttpException
                        val er: String = exception.response()!!.errorBody()!!.string()
                        val errorResponse =
                            Gson().fromJson(er, ErrorResponse::class.java)
                        if (errorResponse != null) {
                            if (errorResponse.message != null && errorResponse.message.length > 0) {
                                listener.onResponseFailure(Constants.ERROR,errorResponse.message)

                            }
                        }

                    } catch (e: Exception) {
                        listener.onResponseFailure(Constants.ERROR,Constants.ERROR_MESSAGE)
                    }
                }

            })

        }catch (e:Exception){

        }
    }
}