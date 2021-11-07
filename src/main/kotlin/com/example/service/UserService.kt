package com.example.service


import com.example.model.Follower
import com.example.model.User
import com.example.repo.PostRepository
import com.example.repo.UserRepository
import com.example.request.CreateUserByEmailRequest
import com.example.request.LikeRequest
import com.example.request.UpdateUsernameRequest

class UserService(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository
) {
    fun updateUsername(
        oldUsername: String,
        newUsername: String
    ) = userRepository.updateUsername(oldUsername, newUsername) > 0

    fun getUser(username: String): User? {
        return userRepository.getUser(username)
    }

    fun insertUser(request: CreateUserByEmailRequest): Int? =
        userRepository.insertUser(email = request.email, password = request.password, name = request.name)

    fun getUserByEmail(email: String): User? =
        userRepository.getUserByEmail(email)

    fun login(email: String, password: String): String =
        userRepository.login(email, password)


    fun getUser(email: String, password: String): User? =
        userRepository.getUser(email, password)

    fun insertFollowRelation(followerUsername: String, username: String): String? {
        userRepository.addConnection(
            followerUid = username,
            username = followerUsername
        ) ?: kotlin.run {
            return null
        }

        return userRepository.addConnection(
            followerUid = followerUsername,
            username = username
        ) ?: kotlin.run {
            return null
        }
    }

    fun getConnections(username: String) =
        userRepository.getConnections(username = username)

    fun addFollowRequest(username: String, followerUsername: String) =
        userRepository.insertFollowRequest(
            followerUid = followerUsername,
            username = username
        )

    fun deleteFollowRequest(followerUsername: String, username: String): Int =
        userRepository.deleteFollowRequest(
            followerUid = followerUsername,
            username = username
        )

    fun getFollowRequests(username: String): List<com.example.model.FollowRequest> =
        userRepository.getFollowRequests(username)

    fun getFollowers(username: String): List<Follower> =
        userRepository.getFollowers(username)

   suspend fun deleteLike(likeRequest: LikeRequest, username: String) : Boolean {
        userRepository
            .deleteLike(likeRequest.pid, username)
            .onSuccess {
                return it > 0
            }
            .onFailure {
                return false
            }
        return false
    }

    fun updateName(username: String, name: String): Boolean =
        userRepository.updateName(username, name) > 0
}