package com.example

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.AuthMethod.method
import com.example.di.mainModule
import com.example.route.*
import com.example.route.Utils.updateTokenIfInvalid
import com.example.route.utils.Constants
import com.example.route.webSocket
import com.google.gson.Gson
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.cio.websocket.*
import io.ktor.http.cio.websocket.WebSockets
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import org.koin.ktor.ext.Koin
import java.time.Duration
import java.util.*

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
    }

    routing {
        postRouting()
        loginRouting(secret, issuer, audience)
        feedRouting()
        userRouting()
        likeRouting()
        connectionRouting()
        signupRouting()
        commentRouting()
        messageRouting()
        followRequestRouting()
    }
}

object AuthMethod {
    const val method = "auth-jwt"
}