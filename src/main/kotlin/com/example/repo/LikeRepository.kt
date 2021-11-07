package com.example.repo

import com.example.request.LikeRequest

interface LikeRepository {
    fun insertLike(like: LikeRequest, username: String): Int
    fun deleteLike(pid: Int, username: String): Int
}