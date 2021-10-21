package com.example.service

import com.example.dp.table.UserTable
import com.example.model.User
import com.example.repo.UserRepository
import com.example.request.UpdateUsernameRequest

class UserService(
    private val userRepository: UserRepository
) {
    suspend fun updateUsername(
        updateUsernameRequest: UpdateUsernameRequest
    ): Boolean = try {
        updateUsernameRequest.let {
            userRepository.updateUsername(it.oldUsername, it.newUsername) > 0
        }
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }

    suspend fun getUser(username: String): User? {
        return userRepository.getUser(username)
    }

    fun createUser(user: User): Boolean =
        userRepository.insertUser(user = user) != null
}