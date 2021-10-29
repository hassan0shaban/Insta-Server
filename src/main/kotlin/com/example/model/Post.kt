package com.example.model

import org.joda.time.DateTime

data class Post(
    val pid: Int? = null,
    val caption: String,
    val username: String,
    val imageUrl: String,
    val time: DateTime
)

data class PostDetails(
    val pid: Int,
    val caption: String,
    val time: DateTime,
    val imageUrl: String?,
    var comments: List<Comment> = emptyList(),
    var likes: List<Like> = emptyList(),
    val user: User,
)