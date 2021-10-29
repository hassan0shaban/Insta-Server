package com.example.model

import org.joda.time.DateTime

data class Comment(
    val pid: Int,
    val content: String,
    val uid: Int,
    val time: DateTime
)