package com.taskapiintegration.view.login.data

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val token: String
)
data class LoginRequest(
    val username: String,
    val password: String
)
