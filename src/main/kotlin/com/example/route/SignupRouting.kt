package com.example.route

import com.example.request.CreateUserByEmailRequest
import com.example.route.LoginMethods.EMAIL
import com.example.route.LoginMethods.FACEBOOK
import com.example.route.Routing.SIGNUP
import com.example.service.UserService
import io.ktor.application.*
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.signupRouting() {

    val userService: UserService by inject()

    post("$SIGNUP/$EMAIL") {
        val request = call.receiveOrNull<CreateUserByEmailRequest>() ?: kotlin.run {
            call.respond(message = "request body is not valid", status = HttpStatusCode.BadRequest)
            return@post
        }

        userService.getUserByEmail(request.email)?.let {
            call.respond(
                message = "User is already found, your username: ${it.username}",
                status = HttpStatusCode.BadRequest
            )
            return@post
        }

        val username = userService.insertUser(request) ?: run {
            call.respond(message = "Cannot create account", status = HttpStatusCode.BadRequest)
            return@post
        }

    }

    post("$SIGNUP/$FACEBOOK") {
        val userId = call.request.headers.get(Parameters.UID) ?: kotlin.run {
            call.respond(message = "must pass the uid", status = HttpStatusCode.BadRequest)
            return@post
        }

        val accessToken = call.request.headers.get(Parameters.ACCESS_TOKEN) ?: kotlin.run {
            call.respond(message = "must pass the access token", status = HttpStatusCode.BadRequest)
            return@post
        }

        HttpClient(CIO) {
            ResponseObserver { response ->
                when (response.status) {
                    HttpStatusCode.OK -> {
                        call.respond("OK".toString())
                    }

                    else -> {
                        call.respond(
                            message = "username or access token is not valid",
                            status = HttpStatusCode.BadRequest
                        )
                    }
                }
            }
        }.apply {
            get<String>("https://graph.facebook.com/$userId?access_token=$accessToken")
        }.close()

        return@post
//        val request = call.receiveOrNull<CreateUserByEmailRequest>() ?: kotlin.run {
//        val request = call.receiveOrNull<CreateUserByEmailRequest>() ?: kotlin.run {
//            call.respond(message = "request body is not valid", status = HttpStatusCode.BadRequest)
//            return@post
//        }

//        userService.getUserByEmail(request.email)?.let {
//            call.respond(
//                message = "User is already found, your username: ${it.username}",
//                status = HttpStatusCode.BadRequest
//            )
//            return@post
//        }
//
//        userService.insertUser(request).let {
//            if (it != null)
//                call.respond(message = "$it", status = HttpStatusCode.OK)
//            else
//                call.respond(message = "Cannot create account", status = HttpStatusCode.BadRequest)
//        }
//    }

    }
}