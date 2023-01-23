package com.example.model

import com.example.utils.TimeFormatter
import org.joda.time.DateTime

data class Comment(
    val postId: Int,
    val comment: String,
    val commentId: Int,
    var username: String? = null,
    var time: String,
    val userImageUrl: String?,
    val profileName: String,
)