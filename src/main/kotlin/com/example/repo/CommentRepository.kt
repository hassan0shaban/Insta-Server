package com.example.repo

import com.example.request.CommentRequest
import com.example.request.LikeRequest

interface CommentRepository {
     fun insertComment(comment: CommentRequest, username: String): Int
     fun deleteComment(commentId: Int): Int
}