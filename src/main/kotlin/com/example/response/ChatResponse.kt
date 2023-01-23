package com.example.response

data class ChatResponse(
    val username: String,
    var name: String,
    var userImageUrl: String?,
    var lastMessage: String,
    var time: String,
)