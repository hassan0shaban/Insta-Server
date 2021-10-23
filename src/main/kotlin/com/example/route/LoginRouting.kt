package com.example.route

import com.example.request.CreateUserByEmailRequest
import com.example.request.FacebookLoginRequest
import com.example.request.LoginByEmailRequest
import com.example.route.LoginMethods.EMAIL
import com.example.route.LoginMethods.FACEBOOK
import com.example.route.Routing.LOGIN
import com.example.route.Routing.SIGNUP
import com.example.service.UserService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.loginRouting() {

    val userService: UserService by inject()

    post("$LOGIN/$EMAIL") {
        val request = call.receiveOrNull<LoginByEmailRequest>() ?: kotlin.run {
            call.respond(message = "request body is not valid", status = HttpStatusCode.BadRequest)
            return@post
        }

        userService.getUser(request.email, request.password) ?: kotlin.run {
            call.respond(
                message = "User or password is not correct",
                status = HttpStatusCode.BadRequest
            )
            return@post
        }

        userService.login(request.email, request.password).let {
            call.respond(message = it, status = HttpStatusCode.OK)
        }
    }
}