package com.example.response

data class LikeNotification(
    override var type: Int,
    override var time: String,
    val likeCount: Int,
    val postId: Int,
    val postImageUrl: String,
    val profileName: String,
) : Notification()