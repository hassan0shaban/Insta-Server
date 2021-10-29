package com.example.service

import com.example.model.*
import com.example.repo.PostRepository
import com.example.repo.UserRepository
import com.example.request.PostRequest
import com.example.request.UpdateUsernameRequest

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

    fun insertPost(postRequest: PostRequest, username: String): Int? =
        postRepository.insertPost(postRequest, username)

    fun getUserPosts(username: String) =
        postRepository.getUserPosts(username = username)

    fun getLikes(pid: Int): List<Like> =
        postRepository.getPostLikes(pid)

    fun getPostComments(pid: Int): List<Comment> =
        postRepository.getPostComments(pid)

    fun getFeedPosts(username: String) : List<PostDetails> {
        val posts = arrayListOf<PostDetails>()
        userRepository
            .getConnections(username)
            .map {
                postRepository.getUserPosts(it.uid)
            }.forEach {
                posts += it
            }
        return posts
    }
}

