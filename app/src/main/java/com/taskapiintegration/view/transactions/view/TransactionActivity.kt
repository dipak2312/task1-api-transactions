package com.taskapiintegration.view.transactions.view

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import com.taskapiintegration.adapter.TransactionListAdapter
import com.taskapiintegration.databinding.ActivityTransactionBinding
import com.taskapiintegration.utils.SecurePrefs
import com.taskapiintegration.view.login.view.LoginActivity
import com.taskapiintegration.view.transactions.viewmodel.TransactionViewModel
import java.util.concurrent.Executors

class TransactionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTransactionBinding
    private lateinit var adapter: TransactionListAdapter

    private val transactionViewModel: TransactionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = TransactionListAdapter(this)
        binding.recyTransactionHistory.adapter = adapter

        showBiometricPrompt()
        observeViewModel()
        onClick()
    }

    private fun onClick() {
        binding.buttonLogout.setOnClickListener {
            SecurePrefs.clearToken(this)
            startActivity(Intent(this@TransactionActivity, LoginActivity::class.java))
            finish()
        }
    }

    private fun showBiometricPrompt() {
        val biometricPrompt = BiometricPrompt(this, Executors.newSingleThreadExecutor(), object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                getTransactions()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(this@TransactionActivity, "Authentication Failed", Toast.LENGTH_SHORT).show()
                showBiometricPrompt()
            }
        })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Login")
            .setSubtitle("Authenticate to access transactions")
            .setNegativeButtonText("Cancel")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }

    private fun getTransactions() {
        val token = SecurePrefs.getToken(this)
        if (!token.isNullOrEmpty()) {
            transactionViewModel.fetchTransactions(token)
        }

        setupSearch()
    }

    private fun observeViewModel() {
        transactionViewModel.transactions.observe(this) { transactions ->
            adapter.addAllItem(transactions)
        }

        transactionViewModel.errorMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupSearch() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
    }
}