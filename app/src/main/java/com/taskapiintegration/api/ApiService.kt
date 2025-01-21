package com.taskapiintegration.api

import com.taskapiintegration.view.login.data.LoginRequest
import com.taskapiintegration.view.login.data.LoginResponse
import com.taskapiintegration.view.transactions.data.TransactionItem
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    fun login(@Body request: LoginRequest): Single<LoginResponse>

    @GET("transactions")
    fun getTransactions(@Header("Authorization") token: String): Single<List<TransactionItem>>
}