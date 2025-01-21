package com.taskapiintegration.view.login.data

data class ErrorResponse(
    val statusCode : Int,
    var error:String,
    var message:String,
    )
