package com.example.repo

import com.example.model.Connection
import com.example.model.User
import com.example.response.ChatResponse

interface UserRepository {
    fun updateUsername(username: String, newUsername: String): Int
    fun getUser(username: String): User?
    fun insertUser(email: String, password: String, name: String): Int?
    fun login(email: String, password: String): String
    fun checkUser(email: String): Int?
    fun getUserByEmail(email: String): User?
    fun getUser(email: String, password: String): User?
    fun getConnections(username: String): List<Connection>
    fun getChatConnections(username: String): List<Connection>
    fun insertConnection(followerUid: String, username: String): Result<Int?>
    fun insertFollowRequest(followerUid: String, username: String): String?
    fun deleteFollowRequest(followerUid: String, username: String): Int
    fun getFollowRequests(username: String): List<com.example.model.FollowRequest>
    fun getFollowers(username: String): List<Connection>
    suspend fun deleteLike(pid: Int, username: String): Result<Int>
    fun updateName(username: String, name: String): Int
}