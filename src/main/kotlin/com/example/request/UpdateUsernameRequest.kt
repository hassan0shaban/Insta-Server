package com.example.request

data class UpdateUsernameRequest(
    val oldUsername: String,
    val newUsername: String
)
