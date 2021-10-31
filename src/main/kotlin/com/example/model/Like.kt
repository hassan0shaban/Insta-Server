package com.example.model

import org.joda.time.DateTime

data class Like(
    val pid: Int,
    val username: String,
    val time: DateTime
)