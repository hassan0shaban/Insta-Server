package com.example.response

data class CommentNotification(
    override var type: Int,
    val likeCount: Int,
    val postId: Int,
    val postImageUrl: String,
    val profileName: String,
    override var time: String,
) : Notification()
