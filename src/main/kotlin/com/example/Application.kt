package com.example

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.AuthMethod.method
import com.example.di.mainModule
import com.example.route.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.routing.*
import org.koin.ktor.ext.Koin

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(ContentNegotiation) { gson { } }
    install(Koin) { modules(mainModule) }

    val secret = environment.config.property("jwt.secret").getString()
    val issuer = environment.config.property("jwt.issuer").getString()
    val audience = environment.config.property("jwt.audience").getString()
    val myRealm = environment.config.property("jwt.realm").getString()

    install(Authentication) {
        jwt(method) {
            realm = myRealm
            verifier(
                JWT.require(Algorithm.HMAC256(secret))
                    .withAudience(audience)
                    .withIssuer(issuer)
                    .build()
            )

            validate { credential ->
                if (credential.payload.getClaim(AuthenticationParameters.USERNAME).asString() != "") {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }
    routing {
        postRouting(secret, issuer, audience)
        feedRouting(secret, issuer, audience)
        loginRouting(secret = secret, issuer = issuer, audience = audience)
        feedRouting(secret = secret, issuer = issuer, audience = audience)
        userRouting(secret = secret, issuer = issuer, audience = audience)
        signupRouting()
    }
}

object AuthMethod {
    const val method = "auth-jwt"
}