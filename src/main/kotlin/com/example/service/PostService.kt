package com.example.service

import com.example.model.*
import com.example.repo.PostRepository
import com.example.repo.UserRepository
import com.example.request.PostRequest
import com.example.request.UpdateUsernameRequest
import com.example.response.PostResponse

class PostService(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository
) {
    fun updateUsername(
        updateUsernameRequest: UpdateUsernameRequest
    ): Boolean = try {
        updateUsernameRequest.let {
            userRepository.updateUsername(it.oldUsername, it.newUsername) > 0
        }
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }

    fun insertPost(caption: String, imageUrl: String, username: String): Int? =
        postRepository.insertPost(caption, imageUrl, username)

    fun getUserPosts(username: String): ArrayList<PostResponse> {
        val posts = arrayListOf<PostResponse>()
        postRepository
            .getUserPosts(username)
            .forEach { post ->
                val user = userRepository.getUser(post.username)
                val comments = postRepository.getPostComments(post.pid)
                val likes = postRepository.getPostLikes(post.pid)
                posts.add(
                    PostResponse(
                        likes = likes,
                        comments = comments,
                        user = user!!,
                        post = post
                    )
                )
            }
        return posts
    }

    fun getPost(pid: Int): PostResponse? =
        postRepository
            .getPost(pid)
            ?.let { post ->
                val likes = postRepository.getPostLikes(pid)
                val comments = postRepository.getPostComments(pid)
                val user = userRepository.getUser(post.username)
                return@let PostResponse(
                    likes = likes,
                    comments = comments,
                    user = user!!,
                    post = post
                )
            }

    fun getLikes(pid: Int): List<Like> =
        postRepository.getPostLikes(pid)

    fun getPostComments(pid: Int): List<Comment> =
        postRepository.getPostComments(pid)

    fun getFeedPosts(username: String): List<PostResponse> {
        val posts = arrayListOf<PostResponse>()
        userRepository
            .getConnections(username)
            .forEach { connection ->
                posts += getUserPosts(connection.username)
            }
        return posts
    }

    fun updatePostImageUrl(pid: Int, imageUrl: String): Int =
        postRepository.updatePostImageUrl(pid, imageUrl)
}

