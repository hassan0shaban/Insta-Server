package com.example.repo

import com.example.model.Follower
import com.example.model.User

interface UserRepository {
    fun updateUsername(username: String, newUsername: String): Int
    fun getUser(username: String): User?
    fun insertUser(email: String, password: String, name: String): Int?
    fun login(email: String, password: String): String
    fun checkUser(email: String): Int?
    fun getUserByEmail(email: String): User?
    fun getUser(email: String, password: String): User?
    fun getConnections(username: String): List<Follower>
    fun addConnection(followerUid: String, username: String): String?
    fun insertFollowRequest(followerUid: String, username: String): String?
    fun deleteFollowRequest(followerUid: String, username: String): Int
    fun getFollowRequests(username: String): List<com.example.model.FollowRequest>
    fun getFollowers(username: String): List<Follower>
}