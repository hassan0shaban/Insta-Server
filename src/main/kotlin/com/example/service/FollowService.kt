package com.example.service

import com.example.repo.PostRepository
import com.example.repo.UserRepository

class FollowService(
    private val userRepository: UserRepository,
    val postRepository: PostRepository
) {

}