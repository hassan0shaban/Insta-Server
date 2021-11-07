package com.example.response

import com.example.utils.TimeFormatter
import org.joda.time.DateTime

data class LikeResponse(
    val pid: Int,
    var username: String? = null,
    var time: String = DateTime().let { TimeFormatter.dateTimeToString(it) },
)