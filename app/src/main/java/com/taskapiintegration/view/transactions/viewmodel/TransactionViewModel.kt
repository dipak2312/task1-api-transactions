package com.taskapiintegration.view.transactions.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.taskapiintegration.api.ApiService
import com.taskapiintegration.view.transactions.data.TransactionItem
import kotlinx.coroutines.launch

class TransactionViewModel(app: Application) : AndroidViewModel(app) {

    private val _transactions = MutableLiveData<List<TransactionItem>>()
    val transactions: LiveData<List<TransactionItem>> get() = _transactions

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val api: ApiService = com.taskapiintegration.application.Application.retrofit.create(ApiService::class.java)

    fun fetchTransactions(token: String) {
        viewModelScope.launch {
            try {
                val data = api.getTransactions(token)
                _transactions.postValue(data)
            } catch (e: Exception) {
                _errorMessage.postValue("Failed to load transactions: ${e.message}")
            }
        }
    }
}
