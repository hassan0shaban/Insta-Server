package com.example.model

data class User(
    val uid: Int? = null,
    val name: String?,
    val username: String? = null,
    val password: String,
    val user_image_url: String? = null,
    val bio: String? = null,
    val email: String? = null,
    val phone_number: String? = null,
)