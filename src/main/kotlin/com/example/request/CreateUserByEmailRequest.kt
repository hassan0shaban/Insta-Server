package com.example.request

data class CreateUserByEmailRequest(
    val email: String,
    val password: String,
)