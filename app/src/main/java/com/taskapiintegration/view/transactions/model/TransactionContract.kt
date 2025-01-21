package com.taskapiintegration.view.transactions.model

import com.taskapiintegration.view.transactions.data.Transaction
import com.taskapiintegration.view.transactions.data.TransactionItem

interface TransactionContract {
    interface View{
        fun showErrorDialog(error: String, message: String)
        fun transactionSuccess(transaction: List<TransactionItem>)

    }
    interface Model{
        fun onTransactionSuccess(transaction: List<TransactionItem>)
        fun onResponseFailure(error: String, message: String)

    }
}