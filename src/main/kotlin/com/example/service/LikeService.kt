package com.example.service

import com.example.repo.CommentRepository
import com.example.repo.LikeRepository
import com.example.request.CommentRequest
import com.example.request.LikeRequest

class LikeService(
    private val commentRepository: CommentRepository,
    private val likeRepository: LikeRepository,
) {

    fun insertLike(like: LikeRequest, username: String): Boolean =
        likeRepository.insertLike(like, username) > 0

    fun deleteLike(likeRequest: LikeRequest, username: String): Boolean =
        likeRepository.deleteLike(likeRequest.pid, username) > 0
}

