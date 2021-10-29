package com.example.repo

import com.example.model.Comment
import com.example.model.Like
import com.example.model.Post
import com.example.model.PostDetails
import com.example.request.PostRequest

interface PostRepository {
    fun getPostComments(pid: Int): List<Comment>
    fun getPostLikes(pid: Int): List<Like>
    fun getUserPosts(username: String): List<PostDetails>
    fun insertPost(postRequest: PostRequest, username: String): Int?
}