package com.example.route

import com.example.AuthMethod
import com.example.route.utils.Utils
import com.example.service.UserService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.connectionRouting() {
    val userService: UserService by inject()

    authenticate(AuthMethod.method) {
        get("/connections") {
            val principal = call.principal<JWTPrincipal>()
            val username = principal!!.payload.getClaim(AuthenticationParameters.USERNAME).asString()

            call.respond(message = userService.getChatConnections(username))
        }
    }

    authenticate(AuthMethod.method) {
        get("/followers") {
            val principal = call.principal<JWTPrincipal>()
            val username = principal!!.payload.getClaim(AuthenticationParameters.USERNAME).asString()

            userService.getFollowers(username).let {
                call.respond(message = it, status = HttpStatusCode.OK)
            }
        }
    }

    authenticate(AuthMethod.method) {
        post("/followers") {
            val followUsername = call.parameters.get(Parameters.USERNAME) ?: kotlin.run {
                call.respond(message = "must pass username", status = HttpStatusCode.BadRequest)
                return@post
            }

            userService.getUser(followUsername) ?: kotlin.run {
                call.respond(message = "username is not correct", status = HttpStatusCode.BadRequest)
                return@post
            }

            val username = Utils.getUsername(call)

            val isSuccess = userService.insertConnection(
                followerUsername = followUsername,
                username = username
            )

            if (isSuccess) {
                call.respond(message = "success", status = HttpStatusCode.Created)
            } else {
                call.respond(message = "failed", status = HttpStatusCode.NotImplemented)
            }
        }
    }
}