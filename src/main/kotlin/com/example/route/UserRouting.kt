package com.example.route

import com.example.AuthMethod
import com.example.request.FollowRequest
import com.example.request.UpdateUsernameRequest
import com.example.route.LoginMethods.EMAIL
import com.example.route.LoginMethods.FACEBOOK
import com.example.route.LoginMethods.Google
import com.example.route.Parameters.Login_Method
import com.example.route.Parameters.Signup_Method
import com.example.route.Parameters.USERNAME
import com.example.route.Routing.LOGIN
import com.example.route.Routing.SIGNUP
import com.example.service.UserService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.userRouting(secret: String, issuer: String, audience: String) {
    val userService: UserService by inject()

    post(SIGNUP) {
        val method = call.parameters[Signup_Method].toString()

        when (method) {
            EMAIL -> {
                call.redirectInternally("$SIGNUP/$EMAIL")
            }

            FACEBOOK -> {
                call.redirectInternally("$SIGNUP/$FACEBOOK")
            }

            Google -> {
                call.redirectInternally("$SIGNUP/$Google")
            }

            else -> {
                call.respond(message = "submit your signup type", status = HttpStatusCode.BadRequest)
            }
        }
    }

    post(LOGIN) {
        val method = call.parameters[Login_Method].toString()

        when (method) {
            EMAIL -> {
                call.redirectInternally("$LOGIN/$EMAIL")
            }

            FACEBOOK -> {
                call.respondRedirect("$LOGIN/$FACEBOOK")
            }

            Google -> {
                call.respondRedirect("$LOGIN/$Google")
            }

            else -> {
                call.respond(message = "submit your login type", status = HttpStatusCode.BadRequest)
            }
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

    authenticate(AuthMethod.method) {
        get("/user/{${USERNAME}/follow-requests") {
            val username = call.parameters["user"]
        }
    }


    authenticate(AuthMethod.method) {
        post("/follow-requests") {
            val followRequest = call.receiveOrNull<FollowRequest>() ?: kotlin.run {
                call.respond(message = "must pass username", status = HttpStatusCode.BadRequest)
                return@post
            }

            userService.getUser(followRequest.username) ?: kotlin.run {
                call.respond(message = "username is not correct", status = HttpStatusCode.BadRequest)
                return@post
            }

            val principal = call.principal<JWTPrincipal>()
            val followerUsername = principal!!
                .payload
                .getClaim(AuthenticationParameters.USERNAME)
                .asString()

            userService.addFollowRequest(
                followerUsername = followerUsername,
                username = followRequest.username
            ) ?: kotlin.run {
                call.respond(message = "cannot insert request", status = HttpStatusCode.NotImplemented)
                return@post
            }

            call.respond(message = "success", status = HttpStatusCode.Created)
        }
    }


    authenticate(AuthMethod.method) {
        delete("/follow-requests") {
            val followUsername = call.receiveOrNull<FollowRequest>() ?: kotlin.run {
                call.respond(message = "must pass username", status = HttpStatusCode.BadRequest)
                return@delete
            }

            userService.getUser(followUsername.username) ?: kotlin.run {
                call.respond(message = "username is not correct", status = HttpStatusCode.BadRequest)
                return@delete
            }

            val principal = call.principal<JWTPrincipal>()
            val username = principal!!
                .payload
                .getClaim(AuthenticationParameters.USERNAME)
                .asString()

            val affected = userService.deleteFollowRequest(
                followerUsername = followUsername.username,
                username = username
            )
            if (affected > 0) {
                call.respond(message = "success", status = HttpStatusCode.OK)
                return@delete
            }

            call.respond(message = "failed", status = HttpStatusCode.NotImplemented)
        }
    }


    authenticate(AuthMethod.method) {
        get("/follow-requests") {
            val principal = call.principal<JWTPrincipal>()
            val username = principal!!
                .payload
                .getClaim(AuthenticationParameters.USERNAME)
                .asString()

            val list = userService.getFollowRequests(
                username = username
            )

            call.respond(message = list, status = HttpStatusCode.OK)
        }
    }

    authenticate(AuthMethod.method) {
        get("/connections") {
            val principal = call.principal<JWTPrincipal>()
            val username = principal!!.payload.getClaim(AuthenticationParameters.USERNAME).asString()

            call.respond(
                message = userService.getConnections(username),
            )
        }
    }

    authenticate(AuthMethod.method) {
        post("/followers") {
            val followUsername = call.parameters.get(USERNAME) ?: kotlin.run {
                call.respond(message = "must pass username", status = HttpStatusCode.BadRequest)
                return@post
            }

            userService.getUser(followUsername) ?: kotlin.run {
                call.respond(message = "username is not correct", status = HttpStatusCode.BadRequest)
                return@post
            }

            val principal = call.principal<JWTPrincipal>()
            val username = principal!!.payload.getClaim(AuthenticationParameters.USERNAME).asString()

            userService.insertFollowRelation(followUsername, username).let {
                if (it != null)
                    call.respond(message = "success", status = HttpStatusCode.OK)
                else
                    call.respond(message = "Cannot update username", status = HttpStatusCode.BadRequest)
            }
        }
    }

    authenticate(AuthMethod.method) {
        get("/followers") {
            val principal = call.principal<JWTPrincipal>()
            val username = principal!!.payload.getClaim(AuthenticationParameters.USERNAME).asString()

            userService.getFollowers(username).let {
                call.respond(message = it, status = HttpStatusCode.OK)
            }
        }
    }


}