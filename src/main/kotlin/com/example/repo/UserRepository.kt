package com.example.repo

import com.example.model.User

interface UserRepository {
    fun updateUsername(username: String, newUsername: String): Int
    fun insertUser(user: User): Int?
    fun getUser(username: String): User?
}