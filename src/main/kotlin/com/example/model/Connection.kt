package com.example.model

import org.joda.time.DateTime

data class Connection(
    val uid: String,
    val followerUid: String,
    val time: DateTime,
)