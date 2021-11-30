package com.example

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.AuthMethod.API_KEY
import com.example.AuthMethod.method
import com.example.AuthMethod.refreshTokenMethod
import com.example.di.mainModule
import com.example.route.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.cio.websocket.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.websocket.*
import org.joda.time.DateTime
import org.koin.ktor.ext.Koin
import java.time.Duration


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(AutoHeadResponse)

    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofHours(1)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }

    install(ContentNegotiation) {
        gson()
    }

    install(Koin) {
        modules(mainModule)
    }

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
                if (credential.payload.getClaim(AuthenticationParameters.USERNAME).asString() != "")
                    JWTPrincipal(payload = credential.payload)
                else
                    null
            }
        }

        jwt(refreshTokenMethod) {
            realm = myRealm
            verifier(
                JWT.require(Algorithm.HMAC256(secret))
                    .build()
            )

            validate { credential ->
                if (credential.payload.getClaim(AuthenticationParameters.USERNAME)
                        .asString() != "" )
                    JWTPrincipal(payload = credential.payload)
                else
                    null
            }
        }
    }

    routing {
        postRouting()
        loginRouting(secret, issuer, audience)
        feedRouting()
        userRouting()
        likeRouting()
        connectionRouting()
        signupRouting()
        search()
        commentRouting()
        experimentRouting(secret, issuer, audience)
        notificationsRouting()
        messageRouting()
        followRequestRouting()
    }
}

object AuthMethod {
    const val API_KEY: String = "api-key"
    const val method = "auth-jwt"
    const val refreshTokenMethod = "refresh-jwt"
}