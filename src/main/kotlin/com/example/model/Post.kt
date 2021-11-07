package com.example.model

data class Post(
    val pid: Int,
    val caption: String,
    val username: String,
    var imageUrl: String,
    val time: String,
)
