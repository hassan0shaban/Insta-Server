package com.example.model

data class User(
    val uid: Int,
    val name: String,
    val username: String,
    val password: String,
    val userImageUrl: String? = null,
    val bio: String? = null,
    val email: String,
    val phoneNumber: String? = null,
)
