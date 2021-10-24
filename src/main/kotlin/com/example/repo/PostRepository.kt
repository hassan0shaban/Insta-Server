package com.example.repo

import com.example.model.Comment
import com.example.model.Like
import com.example.response.Post

interface PostRepository {
    fun getFeedPosts(username: String): List<Post>
    fun getPostComments(pid: Int): List<Comment>
    fun getPostLikes(pid: Int): List<Like>
    fun getUserPosts(username: String): List<Post>
}