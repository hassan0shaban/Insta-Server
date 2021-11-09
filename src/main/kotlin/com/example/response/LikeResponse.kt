package com.example.response

import com.example.utils.TimeFormatter
import org.joda.time.DateTime

data class LikeResponse(
    val postId: Int,
    var username: String,
    var time: String = DateTime().let { TimeFormatter.dateTimeToString(it) },
)