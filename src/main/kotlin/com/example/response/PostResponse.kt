package com.example.response

import com.example.model.Comment
import com.example.model.Like
import com.example.model.Post
import com.example.model.User

data class PostResponse(
    var post: Post,
    var comments: List<Comment> = emptyList(),
    var likes: List<Like> = emptyList(),
    val user: User,
)