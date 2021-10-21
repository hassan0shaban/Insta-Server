package com.example.route

import com.example.model.User
import com.example.request.UpdateUsernameRequest
import com.example.service.UserService
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import org.koin.ktor.ext.inject

fun Route.userRouting() {
    val userService: UserService by inject()

    post("/users/create") {
        val user = call.receiveOrNull<User>() ?: kotlin.run {
            call.respond(message = "request body is not valid", status = HttpStatusCode.BadRequest)
            return@post
        }

        userService.createUser(user).let {
            if (it)
                call.respond(message = "success", status = HttpStatusCode.OK)
            else
                call.respond(message = "Cannot insert user", status = HttpStatusCode.BadRequest)
        }
    }

    get("/users/{username}") {
        val username = call.parameters.get("username")
        if (username == null)
            call.respond(message = "null", status = HttpStatusCode.BadRequest).also { return@get }

        val user = userService.getUser(username!!)

        if (user == null)
            call.respond(message = "User is not found", status = HttpStatusCode.NotFound)
        else
            call.respond(message = user, status = HttpStatusCode.OK)
    }

    post("/users/update-username") {
        val updateUsernameRequest = call.receiveOrNull<UpdateUsernameRequest>() ?: kotlin.run {
            call.respond(message = "request body is not valid", status = HttpStatusCode.BadRequest)
            return@post
        }

        userService.updateUsername(updateUsernameRequest).let {
            if (it)
                call.respond(message = "success", status = HttpStatusCode.OK)
            else
                call.respond(message = "Cannot update username", status = HttpStatusCode.BadRequest)
        }
    }
}
