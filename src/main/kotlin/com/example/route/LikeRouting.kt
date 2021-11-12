package com.example.route

import com.example.AuthMethod
import com.example.request.LikeRequest
import com.example.service.LikeService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.likeRouting() {
    val likeService: LikeService by inject()

    authenticate(AuthMethod.method) {
        delete("/like") {
            val likeRequest = call.receiveOrNull<LikeRequest>() ?: kotlin.run {
                call.respond(message = "wrong info", status = HttpStatusCode.BadRequest)
                return@delete
            }

            val username = Utils.getUsername(call)

            likeService.deleteLike(likeRequest, username).let {
                if (it)
                    call.respond(message = "success", status = HttpStatusCode.OK)
                else
                    call.respond(message = "wrong info", status = HttpStatusCode.NotImplemented)
            }
        }
    }

    authenticate(AuthMethod.method) {
        post("/like") {
            val request = call.receiveOrNull<LikeRequest>() ?: kotlin.run {
                call.respond(message = "wrong info", status = HttpStatusCode.BadRequest)
                return@post
            }

            val username = Utils.getUsername(call)

            if (likeService.checkLike(username, request.pid)) {
                if (likeService.deleteLike(request, username)) {
                    call.respond(message = "success", status = HttpStatusCode.OK)
                } else {
                    call.respond(message = "cannot delete comment", status = HttpStatusCode.NotImplemented)
                }
                return@post
            } else {
                if (likeService.insertLike(request, username)) {
                    call.respond(message = "success", status = HttpStatusCode.Created)
                } else {
                    call.respond(message = "cannot insert comment", status = HttpStatusCode.NotImplemented)
                }
                return@post
            }
        }
    }
}