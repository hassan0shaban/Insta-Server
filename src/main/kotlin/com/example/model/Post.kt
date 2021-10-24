package com.example.model

import org.joda.time.DateTime

data class Post(
    val pid: Int,
    val caption: String,
    val uid: String,
    val imageUrl: String,
    val time: DateTime
)
