package com.example.request

data class LoginByEmailRequest(
    val email: String,
    val password: String,
)