package com.taskapiintegration.repository

import com.taskapiintegration.api.ApiService
import com.taskapiintegration.application.Application
import com.taskapiintegration.view.transactions.data.TransactionItem

class TransactionRepository {
    private val retrofit = Application.retrofit.create(ApiService::class.java)

    suspend fun getTransactions(token: String): Result<List<TransactionItem>> {
        return try {
            val response = retrofit.getTransactions(token)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
