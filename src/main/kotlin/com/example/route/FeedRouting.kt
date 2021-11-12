package com.example.route

import com.example.AuthMethod
import com.example.route.AuthenticationParameters.USERNAME
import com.example.service.PostService
import com.example.service.UserService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.feedRouting() {

    val userService: UserService by inject()
    val postService: PostService by inject()

    authenticate(AuthMethod.method) {
        get("/feed") {
            val principal = call.principal<JWTPrincipal>()
            val username = principal!!.payload.getClaim(USERNAME).asString()

            call.respond(
                postService.getFeedPosts(username)
            )
        }
    }
}