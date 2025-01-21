package com.taskapiintegration.view.transactions.presenter

import android.content.Context
import com.taskapiintegration.constants.ConstantsSP
import com.taskapiintegration.utils.SharedPreference
import com.taskapiintegration.view.transactions.data.TransactionItem
import com.taskapiintegration.view.transactions.interactor.TransactionInteractor
import com.taskapiintegration.view.transactions.model.TransactionContract

class TransactionPresenter(private val view: TransactionContract.View, private val interactor: TransactionInteractor):
    TransactionContract.Model {
    override fun onTransactionSuccess(transaction: List<TransactionItem>) {
        view.transactionSuccess(transaction)
    }
    override fun onResponseFailure(error: String, message: String) {
        view.showErrorDialog(error,message)
    }

    fun getTransactionList(context: Context){
        val token = SharedPreference.getString(context, ConstantsSP.ACCESS_TOKEN)
        if (token != null) {
            interactor.getTransactions(this,token)
        }
    }
}