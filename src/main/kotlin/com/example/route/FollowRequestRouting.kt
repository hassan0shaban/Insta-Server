package com.example.route

import com.example.AuthMethod
import com.example.request.Username
import com.example.service.UserService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.followRequestRouting() {
    val userService: UserService by inject()

    authenticate(AuthMethod.method) {
        post("/follow-requests") {
            val username = call.receiveOrNull<Username>() ?: kotlin.run {
                call.respond(message = "must pass username", status = HttpStatusCode.BadRequest)
                return@post
            }

            userService.getUser(username.username) ?: kotlin.run {
                call.respond(message = "username is not correct", status = HttpStatusCode.BadRequest)
                return@post
            }

            val principal = call.principal<JWTPrincipal>()
            val followerUsername = principal!!
                .payload
                .getClaim(AuthenticationParameters.USERNAME)
                .asString()

            userService.addFollowRequest(
                followerUsername = followerUsername,
                username = username.username
            ) ?: kotlin.run {
                call.respond(message = "cannot insert request", status = HttpStatusCode.NotImplemented)
                return@post
            }

            call.respond(message = "success", status = HttpStatusCode.Created)
        }
    }

    authenticate(AuthMethod.method) {
        put("/follow-requests") {
            val followRequest = call.receiveOrNull<Username>() ?: kotlin.run {
                call.respond(message = "must pass username", status = HttpStatusCode.BadRequest)
                return@put
            }

            userService.getUser(followRequest.username) ?: kotlin.run {
                call.respond(message = "username is not correct", status = HttpStatusCode.BadRequest)
                return@put
            }

            val principal = call.principal<JWTPrincipal>()
            val username = principal!!
                .payload
                .getClaim(AuthenticationParameters.USERNAME)
                .asString()

            val isSuccess = userService.insertConnection(
                followerUsername = followRequest.username,
                username = username
            )

            if (isSuccess) {
                call.respond(message = "success", status = HttpStatusCode.Created)
            } else {
                call.respond(message = "failed", status = HttpStatusCode.NotImplemented)
            }
        }
    }


    authenticate(AuthMethod.method) {
        delete("/follow-requests") {
            val followUsername = call.receiveOrNull<Username>() ?: kotlin.run {
                call.respond(message = "must pass username", status = HttpStatusCode.BadRequest)
                return@delete
            }

            userService.getUser(followUsername.username) ?: kotlin.run {
                call.respond(message = "username is not correct", status = HttpStatusCode.BadRequest)
                return@delete
            }

            val principal = call.principal<JWTPrincipal>()
            val username = principal!!
                .payload
                .getClaim(AuthenticationParameters.USERNAME)
                .asString()

            userService.deleteFollowRequest(
                followerUsername = followUsername.username,
                username = username
            )

            userService.deleteFollowRequest(
                followerUsername = username,
                username = followUsername.username
            )

            call.respond(message = "success", status = HttpStatusCode.OK)
            return@delete
        }
    }


    authenticate(AuthMethod.method) {
        get("/follow-requests") {
            val principal = call.principal<JWTPrincipal>()
            val username = principal!!
                .payload
                .getClaim(AuthenticationParameters.USERNAME)
                .asString()

            val list = userService.getFollowRequests(
                username = username
            )

            call.respond(message = list, status = HttpStatusCode.OK)
        }
    }


}