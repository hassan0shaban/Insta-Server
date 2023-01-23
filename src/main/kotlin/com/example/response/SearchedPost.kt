package com.example.response

data class SearchedPost(
    val postId: Int,
    val caption: String,
    val username: String,
    val profileName: String,
    var postImageUrl: String,
    val time: String,
    val userImageUrl: String? = null,
)
