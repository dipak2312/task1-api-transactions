package com.taskapiintegration.view.transactions.data

import com.google.gson.annotations.SerializedName

data class TransactionItem(
    val amount: String,
    val category: String,
    val date: String,
    val description: String,
    val id: String
)