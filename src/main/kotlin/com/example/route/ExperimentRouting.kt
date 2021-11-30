package com.example.route

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.AuthMethod
import com.example.route.utils.Constants
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.response.*
import io.ktor.routing.*
import java.util.*


fun Route.experimentRouting(secret: String, issuer: String, audience: String) {

    get("experiment-token") {
        val username = "experiment"
        val token = JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim(AuthenticationParameters.USERNAME, username)
            .withClaim(AuthenticationParameters.COUNT, 15)
            .withExpiresAt(Date(System.currentTimeMillis() + Constants.`5 min`))
            .sign(Algorithm.HMAC256(secret))

        call.respond(hashMapOf("token" to token))
    }
}