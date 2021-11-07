package com.example.service

import com.example.repo.CommentRepository
import com.example.request.CommentRequest
import com.example.request.LikeRequest

class CommentService(
    private val commentRepository: CommentRepository,
) {
     fun insertComment(comment: CommentRequest, username: String): Boolean =
         commentRepository.insertComment(comment, username) > 0

    fun deleteComment(commentId: Int): Boolean =
        commentRepository.deleteComment(commentId) > 0
}

