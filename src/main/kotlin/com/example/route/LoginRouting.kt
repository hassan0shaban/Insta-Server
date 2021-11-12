package com.example.route

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.request.LoginByEmailRequest
import com.example.route.AuthenticationParameters.USERNAME
import com.example.route.LoginMethods.EMAIL
import com.example.route.Routing.LOGIN
import com.example.route.utils.Constants.threeMonths
import com.example.service.UserService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import java.util.*

fun Route.loginRouting(secret: String, issuer: String, audience: String) {

    val userService: UserService by inject()

    post("$LOGIN/$EMAIL") {
        val request = call.receiveOrNull<LoginByEmailRequest>() ?: kotlin.run {
            call.respond(message = "request body is not valid", status = HttpStatusCode.BadRequest)
            return@post
        }

        val user = userService.getUser(request.email, request.password) ?: kotlin.run {
            call.respond(
                message = "User or password is not correct",
                status = HttpStatusCode.BadRequest
            )
            return@post
        }

        val token = JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim(USERNAME, user.username)
            .withExpiresAt(Date(System.currentTimeMillis() + threeMonths))
            .sign(Algorithm.HMAC256(secret))

        call.respond(hashMapOf("token" to token))
    }
}