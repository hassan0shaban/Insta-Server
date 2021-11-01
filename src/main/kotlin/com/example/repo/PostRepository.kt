package com.example.repo

import com.example.model.Comment
import com.example.model.Like
import com.example.model.Post
import com.example.request.PostRequest

interface PostRepository {
    fun getPostComments(pid: Int): List<Comment>
    fun getPostLikes(pid: Int): List<Like>
    fun insertPost(caption: String, imageUrl: String, username: String): Int?
    fun getUserPosts(username: String, limit: Int = Constants.UserPostsLimit): List<Post>
    fun getPost(pid: Int): Post?
    fun updatePostImageUrl(pid: Int, imageUrl: String): Int
}