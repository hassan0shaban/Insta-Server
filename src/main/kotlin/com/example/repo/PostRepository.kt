package com.example.repo

import com.example.model.Comment
import com.example.response.LikeResponse
import com.example.model.Post

interface PostRepository {
    fun getPostComments(pid: Int): List<Comment>
    fun getPostLikes(pid: Int): List<LikeResponse>
    fun insertPost(caption: String, username: String, type: Int): Int?
    fun getUserPosts(username: String, page: Int, pageSize: Int): List<Post>
    fun getPost(pid: Int): Post?
    fun updatePostImageUrl(pid: Int, imageUrl: String): Int
}