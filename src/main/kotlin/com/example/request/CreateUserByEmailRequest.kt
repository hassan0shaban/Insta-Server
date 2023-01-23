package com.example.request

data class CreateUserByEmailRequest(
    val email: String,
    val name: String,
    val password: String,
)