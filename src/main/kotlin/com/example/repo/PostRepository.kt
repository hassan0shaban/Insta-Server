package com.example.repo

import com.example.model.Comment
import com.example.model.Like
import com.example.model.Post
import com.example.model.User
import com.example.response.PostResponse
import com.example.request.PostRequest

interface PostRepository {
    fun getPostComments(pid: Int): List<Comment>
    fun getPostLikes(pid: Int): List<Like>
    fun insertPost(postRequest: PostRequest, username: String): Int?
    fun getUserPosts(username: String, limit: Int = Constants.UserPostsLimit) : List<Post>
    fun getPost(pid: Int): Post?
}