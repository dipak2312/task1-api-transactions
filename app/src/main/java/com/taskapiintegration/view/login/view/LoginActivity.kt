package com.taskapiintegration.view.login.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.taskapiintegration.constants.ConstantsSP
import com.taskapiintegration.databinding.ActivityLoginBinding
import com.taskapiintegration.utils.SharedPreference
import com.taskapiintegration.view.login.data.LoginResponse
import com.taskapiintegration.view.login.interactor.LoginInteractor
import com.taskapiintegration.view.login.model.LoginContract
import com.taskapiintegration.view.login.presenter.LoginPresenter
import com.taskapiintegration.view.transactions.view.TransactionActivity

class LoginActivity : AppCompatActivity(), LoginContract.View {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onClick()
        getControl()
    }
    private fun getControl(){
        presenter = LoginPresenter(this@LoginActivity, LoginInteractor())
    }

    private fun onClick() {
        binding.buttonLogin.setOnClickListener {
            presenter.login(binding)
        }
    }



    override fun showErrorDialog(error: String, message: String) {

    }

    override fun loginSuccess(loginResponse: LoginResponse) {
        if (loginResponse.success){
            if (loginResponse.token.isNotEmpty()){
                SharedPreference.putString(this, ConstantsSP.ACCESS_TOKEN, loginResponse.token)
                val intent = Intent(this@LoginActivity, TransactionActivity::class.java)
                startActivity(intent)
                finish()
            }

        }
    }

}