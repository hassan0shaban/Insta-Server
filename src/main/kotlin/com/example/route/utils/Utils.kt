package com.example.route.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.request.Username
import com.example.route.AuthenticationParameters
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.http.*
import io.ktor.request.*
import java.util.*

suspend fun ApplicationCall.redirectInternally(path: String) {
    val cp = object : RequestConnectionPoint by this.request.local {
        override val uri: String = path
    }
    val req = object : ApplicationRequest by this.request {
        override val local: RequestConnectionPoint = cp
    }
    val call = object : ApplicationCall by this {
        override val request: ApplicationRequest = req
    }

    this.application.execute(call, subject = Unit)
}


object Utils {

    fun getUsername(call: ApplicationCall): String =
        call.principal<JWTPrincipal>().let {
            it!!.payload.getClaim(AuthenticationParameters.USERNAME).asString()
        }

    fun createUserJwt(audience : String, issuer : String, secret : String, username: String): String =
        JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim(AuthenticationParameters.USERNAME, username)
            .withExpiresAt(Date(System.currentTimeMillis() + Constants.threeMonths))
            .sign(Algorithm.HMAC256(secret))
}