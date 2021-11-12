package com.example.request

import com.example.utils.TimeFormatter
import org.joda.time.DateTime

data class DeleteCommentRequest(
    val commentId: Int,
)