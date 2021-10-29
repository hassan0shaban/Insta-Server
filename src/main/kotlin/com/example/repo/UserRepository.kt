package com.example.repo

import com.example.model.Connection
import com.example.model.Post
import com.example.model.User

interface UserRepository {
    fun updateUsername(username: String, newUsername: String): Int
    fun getUser(username: String): User?
    fun insertUser(email: String, password: String): Int?
    fun login(email: String, password: String): String
    fun checkUser(email: String): Int?
    fun getUserByEmail(email: String): User?
    fun getUser(email: String, password: String): User?
    fun getConnections(username: String): List<Connection>
}