package com.example.route

import com.example.AuthMethod
import com.example.request.PostRequest
import com.example.route.Parameters.PID
import com.example.route.Parameters.USERNAME
import com.example.service.PostService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.postRouting(secret: String, issuer: String, audience: String) {

    val postService: PostService by inject()

    authenticate(AuthMethod.method) {
        get("/{$USERNAME}/posts") {
            val username = call.parameters[USERNAME] ?: kotlin.run {
                call.respond(message = "username is not correct", status = HttpStatusCode.BadRequest)
                return@get
            }

            call.respond(
                message = postService.getUserPosts(username),
                status = HttpStatusCode.OK
            )
        }
    }

    authenticate(AuthMethod.method) {
        get("/profile") {
//            val username = call.parameters[USERNAME] ?: kotlin.run {
//                call.respond(message = "username is not correct", status = HttpStatusCode.BadRequest)
//                return@get
//            }
            val principal = call.principal<JWTPrincipal>()
            val username = principal!!.payload.getClaim(AuthenticationParameters.USERNAME).asString()

            call.respond(
                message = postService.getUserPosts(username),
                status = HttpStatusCode.OK
            )
        }
    }

    authenticate(AuthMethod.method) {
        get("/posts/{$PID}") {
            val pid = call.parameters[PID]?.toInt() ?: kotlin.run {
                call.respond(message = "username is not correct", status = HttpStatusCode.BadRequest)
                return@get
            }

            val post = postService.getPost(pid) ?: kotlin.run {
                call.respond(message = "cannot insert post", status = HttpStatusCode.BadRequest)
                return@get
            }

            call.respond(
                message = post,
                status = HttpStatusCode.OK
            )
        }
    }

    authenticate(AuthMethod.method) {
        get("/posts/{$PID}/comments") {
            val pid = call.parameters[PID]?.toInt()  ?: kotlin.run {
                call.respond(message = "post id must be passed", status = HttpStatusCode.BadRequest)
                return@get
            }

            call.respond(
                message = postService.getPostComments(pid),
                status = HttpStatusCode.OK
            )
        }
    }

    authenticate(AuthMethod.method) {
        get("/posts/{$PID}/likes") {
            val pid = call.parameters[PID]?.toInt() ?: kotlin.run {
                call.respond(message = "post id must be passed", status = HttpStatusCode.BadRequest)
                return@get
            }

            call.respond(
                message = postService.getLikes(pid),
                status = HttpStatusCode.OK
            )
        }
    }

    authenticate(AuthMethod.method) {
        post("/post") {
            val post = call.receiveOrNull<PostRequest>() ?: kotlin.run {
                call.respond(message = "post must be passed", status = HttpStatusCode.BadRequest)
                return@post
            }

            val principal = call.principal<JWTPrincipal>()
            val username = principal!!.payload.getClaim(AuthenticationParameters.USERNAME).asString()

            val pid = postService.insertPost(post, username) ?: kotlin.run {
                call.respond(message = "cannot create post", status = HttpStatusCode.ExpectationFailed)
                return@post
            }

            call.respond(
                message = "$pid",
                status = HttpStatusCode.OK
            )
        }
    }
}