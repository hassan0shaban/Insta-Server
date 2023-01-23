package com.example.request

data class MessageInsertRequest(
    val receiver: String,
    val message: String,
)