package com.example.route

import com.example.AuthMethod
import com.example.request.CommentRequest
import com.example.request.DeleteCommentRequest
import com.example.route.utils.Utils.getUsername
import com.example.service.CommentService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.commentRouting() {
    val commentService: CommentService by inject()

    authenticate(AuthMethod.method) {
        post("/comment") {
            val comment = call.receiveOrNull<CommentRequest>() ?: kotlin.run {
                call.respond(message = "wrong info", status = HttpStatusCode.BadRequest)
                return@post
            }

            val username = getUsername(call)

            if (commentService.insertComment(comment, username)) {
                call.respond(message = "success", status = HttpStatusCode.Created)
            } else {
                call.respond(message = "cannot insert comment", status = HttpStatusCode.NotImplemented)
            }
        }
    }

    authenticate(AuthMethod.method) {
        delete("/comment") {
            val comment = call.receiveOrNull<DeleteCommentRequest>() ?: kotlin.run {
                call.respond(message = "wrong info", status = HttpStatusCode.BadRequest)
                return@delete
            }

            if (commentService.deleteComment(comment.commentId)) {
                call.respond(message = "success", status = HttpStatusCode.Created)
            } else {
                call.respond(message = "cannot insert comment", status = HttpStatusCode.NotImplemented)
            }
        }
    }

}