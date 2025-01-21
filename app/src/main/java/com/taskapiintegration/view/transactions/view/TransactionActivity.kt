package com.taskapiintegration.view.transactions.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import com.taskapiintegration.adapter.TransactionListAdapter
import com.taskapiintegration.constants.ConstantsSP
import com.taskapiintegration.databinding.ActivityTransactionBinding
import com.taskapiintegration.utils.SharedPreference
import com.taskapiintegration.view.login.view.LoginActivity
import com.taskapiintegration.view.transactions.data.TransactionItem
import com.taskapiintegration.view.transactions.interactor.TransactionInteractor
import com.taskapiintegration.view.transactions.model.TransactionContract
import com.taskapiintegration.view.transactions.presenter.TransactionPresenter
import java.util.concurrent.Executors

class TransactionActivity : AppCompatActivity(), TransactionContract.View {
    private lateinit var binding: ActivityTransactionBinding
    private lateinit var adapter : TransactionListAdapter
    private lateinit var presenter: TransactionPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showBiometricPrompt()
        onClick()

    }

    private fun onClick() {
        binding.buttonLogout.setOnClickListener {
            SharedPreference.remove(this, ConstantsSP.ACCESS_TOKEN)
            val intent = Intent(this@TransactionActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
    private fun showBiometricPrompt() {
        val biometricPrompt = BiometricPrompt(this, Executors.newSingleThreadExecutor(), object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                // Proceed to fetch transactions
                getControl()

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
    private fun getControl() {
        presenter = TransactionPresenter(this@TransactionActivity, TransactionInteractor())
        presenter.getTransactionList(this@TransactionActivity)
        adapter = TransactionListAdapter(this)
        runOnUiThread {
            binding.recyTransactionHistory.adapter = adapter
        }

    }

    override fun showErrorDialog(error: String, message: String) {

    }

    override fun transactionSuccess(transaction:List<TransactionItem>) {
        if (transaction.size != null){
            val expenses = listOf(transaction)
            adapter.addAllItem(transaction)
        }
    }
}