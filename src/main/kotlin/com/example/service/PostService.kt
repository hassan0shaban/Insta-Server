package com.example.service

import com.example.model.*
import com.example.repo.PostRepository
import com.example.repo.UserRepository
import com.example.request.UpdateUsernameRequest
import com.example.response.LikeResponse
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

    fun insertPost(parts: HashMap<String, Any?>, username: String): Int? =
        postRepository.insertPost(
            caption = parts.get("caption") as String,
            type = (parts.get("type") as String).toInt(),
            username = username
        )

    fun getUserPosts(username: String, page: Int, pageSize: Int): ArrayList<PostResponse> {
        val posts = arrayListOf<PostResponse>()
        postRepository
            .getUserPosts(username, page, pageSize)
            .forEach { post ->
                val user = userRepository.getUser(post.username)
                val comments = postRepository.getPostComments(post.postId)
                val likes = postRepository.getPostLikes(post.postId)
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

    fun getLikes(pid: Int): List<LikeResponse> =
        postRepository.getPostLikes(pid)

    fun getPostComments(pid: Int): List<Comment> =
        postRepository.getPostComments(pid)

    fun getFeedPosts(username: String, page: Int, pageSize: Int): List<PostResponse> {
        val posts = arrayListOf<PostResponse>()
        userRepository
            .getConnections(username)
            .forEach { connection ->
                posts += getUserPosts(connection.username, page, pageSize)
            }
        return posts.sortedByDescending { it.post.time }
    }

    fun updatePostImageUrl(pid: Int, imageUrl: String): Int =
        postRepository.updatePostImageUrl(pid, imageUrl)
}

