package com.example.service


import com.example.model.Post
import com.example.model.PostDetails
import com.example.model.User
import com.example.repo.PostRepository
import com.example.repo.UserRepository
import com.example.request.CreateUserByEmailRequest
import com.example.request.UpdateUsernameRequest

class UserService(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository
) {
    fun updateUsername(
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

    fun insertUser(request: CreateUserByEmailRequest): Int? =
        userRepository.insertUser(email = request.email, password = request.password)

    fun getUserByEmail(email: String): User? =
        userRepository.getUserByEmail(email)

    fun login(email: String, password: String): String =
        userRepository.login(email, password)


    fun getUser(email: String, password: String): User? =
        userRepository.getUser(email, password)

}

