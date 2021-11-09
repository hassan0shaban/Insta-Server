package com.example.response

import org.joda.time.DateTime

data class ConnectionResponse(
    val username: String,
    val userName: String,
    val userImageUrl: String,
    val followerUid: String,
    val time: String,
)
