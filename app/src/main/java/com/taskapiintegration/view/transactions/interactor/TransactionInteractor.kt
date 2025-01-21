package com.taskapiintegration.view.transactions.interactor

import com.google.gson.Gson
import com.taskapiintegration.api.ApiService
import com.taskapiintegration.application.Application
import com.taskapiintegration.constants.Constants
import com.taskapiintegration.view.login.data.ErrorResponse
import com.taskapiintegration.view.transactions.data.TransactionItem
import com.taskapiintegration.view.transactions.model.TransactionContract
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class TransactionInteractor {

    fun getTransactions(listener: TransactionContract.Model, token:String) {
        try {
            val retrofit = Application.retrofit.create(ApiService::class.java)
            val response: Single<List<TransactionItem>> = retrofit.getTransactions(token)
            response.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DisposableSingleObserver<List<TransactionItem>>() {
                    override fun onSuccess(transactions: List<TransactionItem>) {
                        listener.onTransactionSuccess(transactions)
                    }

                    override fun onError(e: Throwable) {
                        try {
                            if (e is HttpException) {
                                val errorBody = e.response()?.errorBody()?.string()
                                val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                                if (errorResponse?.message != null && errorResponse.message.isNotEmpty()) {
                                    listener.onResponseFailure(Constants.ERROR, errorResponse.message)
                                } else {
                                    listener.onResponseFailure(Constants.ERROR, Constants.ERROR_MESSAGE)
                                }
                            } else {
                                listener.onResponseFailure(Constants.ERROR, e.message ?: Constants.ERROR_MESSAGE)
                            }
                        } catch (ex: Exception) {
                            listener.onResponseFailure(Constants.ERROR, Constants.ERROR_MESSAGE)
                        }
                    }
                })
        } catch (e: Exception) {

        }
    }
}