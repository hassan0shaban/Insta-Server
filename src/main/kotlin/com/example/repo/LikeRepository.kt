package com.example.repo

import com.example.request.LikeRequest
import org.jetbrains.exposed.sql.ResultRow

interface LikeRepository {
    fun insertLike(like: LikeRequest, username: String): Int
    fun deleteLike(pid: Int, username: String): Int
    fun getLike(username: String, pid: Int): ResultRow?
}