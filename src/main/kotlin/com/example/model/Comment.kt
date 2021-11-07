package com.example.model

import com.example.utils.TimeFormatter
import org.joda.time.DateTime

data class Comment(
    val pid: Int,
    val content: String,
    val commentId: Int,
    var username: String? = null,
    var time: String = DateTime().let { TimeFormatter.dateTimeToString(it) },
)