package com.taskapiintegration.view.login.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.taskapiintegration.constants.ConstantsSP
import com.taskapiintegration.databinding.ActivityLoginBinding
import com.taskapiintegration.repository.LoginRepository
import com.taskapiintegration.utils.SecurePrefs
import com.taskapiintegration.view.login.data.LoginResponse
import com.taskapiintegration.view.transactions.view.TransactionActivity
import com.taskapiintegration.view.login.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.buttonLogin.setOnClickListener {
            val userName = binding.userName.text.toString()
            val password = binding.password.text.toString()
            loginViewModel.login(userName, password)
        }
    }

    private fun setupObservers() {
        loginViewModel.loginResult.observe(this, Observer { result ->
            result.onSuccess { loginSuccess(it) }
            result.onFailure { showError(it.message ?: "Login failed") }
        })
    }

    private fun loginSuccess(loginResponse: LoginResponse) {
        if (loginResponse.success && loginResponse.token.isNotEmpty()) {
            SecurePrefs.saveToken(this@LoginActivity, loginResponse.token)
            startActivity(Intent(this, TransactionActivity::class.java))
            finish()
        }
    }

    private fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }
}
