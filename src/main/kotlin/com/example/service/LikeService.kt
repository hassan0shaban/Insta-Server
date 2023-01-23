package com.example.service

import com.example.maper.Mapper
import com.example.repo.CommentRepository
import com.example.repo.LikeRepository
import com.example.request.CommentRequest
import com.example.request.LikeRequest
import com.example.response.LikeResponse

class LikeService(
    private val commentRepository: CommentRepository,
    private val likeRepository: LikeRepository,
) {

    fun insertLike(like: LikeRequest, username: String): Boolean =
        likeRepository.insertLike(like, username) > 0

    fun deleteLike(likeRequest: LikeRequest, username: String): Boolean =
        likeRepository.deleteLike(likeRequest.pid, username) > 0

    fun getLike(username: String, pid: Int): LikeResponse? =
        likeRepository.getLike(username, pid)?.let {
            Mapper.getLikeFromResultRow(it)
        }

    fun checkLike(username: String, pid: Int) =
        likeRepository.getLike(username, pid) != null
}

