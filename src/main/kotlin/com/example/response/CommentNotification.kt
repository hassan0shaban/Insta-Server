package com.example.response

data class CommentNotification(
    override var type: Int,
    override var time: String,
    val commentCount: Int,
    val postId: Int,
    val postImageUrl: String,
    val profileName: String,
) : Notification()
