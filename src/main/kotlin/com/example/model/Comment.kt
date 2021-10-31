package com.example.model

import org.joda.time.DateTime

data class Comment(
    val pid: Int,
    val content: String,
    val username: String,
    val time: DateTime
)