package com.example.model

import java.sql.Timestamp

data class Post(
    val pid: Int,
    val caption: String,
    val uid: Int,
    val time: Timestamp
)
