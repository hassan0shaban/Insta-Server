package com.example.route

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.AuthMethod
import com.example.request.CreateUserByEmailRequest
import com.example.request.LoginByEmailRequest
import com.example.response.FacebookProfileResponse
import com.example.route.AuthenticationParameters.USERNAME
import com.example.route.LoginMethods.EMAIL
import com.example.route.Routing.LOGIN
import com.example.route.utils.Constants
import com.example.route.utils.Constants.threeMonths
import com.example.route.utils.Utils
import com.example.route.utils.Utils.createUserJwt
import com.example.service.UserService
import com.google.gson.Gson
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
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

        val token = createUserJwt(audience, issuer, secret, user.username)

        call.respond(hashMapOf("token" to token))
    }

    authenticate(AuthMethod.refreshTokenMethod) {
        get("/refresh-token") {
            val username = Utils.getUsername(call)

            val token = JWT.create()
                .withAudience(audience)
                .withIssuer(issuer)
                .withClaim(AuthenticationParameters.USERNAME, username)
                .withExpiresAt(Date(System.currentTimeMillis() + Constants.threeMonths))
                .sign(Algorithm.HMAC256(secret))

            call.respond(token)
        }
    }

    post("$LOGIN/${LoginMethods.FACEBOOK}") {
        val accessToken = call.parameters.get(Parameters.ACCESS_TOKEN) ?: kotlin.run {
            call.respond(message = "access token is required", status = HttpStatusCode.BadRequest)
            return@post
        }

        val facebookProfileUrl =
            "https://graph.facebook.com/v12.0/me?fields=id%2Cname%2Cabout%2Cquotes%2Cemail&access_token=$accessToken"

        kotlin.runCatching {
            HttpClient(CIO).get<String>(facebookProfileUrl).let {
                println(it)
                Gson().fromJson(it, FacebookProfileResponse::class.java)
            }
        }.onFailure {
            call.respond(message = "wrong access token", status = HttpStatusCode.BadRequest)
        }.onSuccess {
            val user = userService.getUserByEmail(it.email)
            if (user != null) {
                val token = createUserJwt(audience, issuer, secret, user.username)
                call.respond(message = token, status = HttpStatusCode.OK)
            } else {
                val username = userService.insertUser(CreateUserByEmailRequest(it.email, it.name, "password")) ?: kotlin.run {
                    call.respond(message = "cannot insert user", status = HttpStatusCode.NotImplemented)
                    return@post
                }

                val token = createUserJwt(audience, issuer, secret, username.toString())
                call.respond(message = token, status = HttpStatusCode.OK)
            }
        }
    }
}