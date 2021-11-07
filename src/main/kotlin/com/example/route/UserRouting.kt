package com.example.route

import com.example.AuthMethod
import com.example.request.UpdateName
import com.example.request.Username
import com.example.route.LoginMethods.EMAIL
import com.example.route.LoginMethods.FACEBOOK
import com.example.route.LoginMethods.Google
import com.example.route.Parameters.Login_Method
import com.example.route.Parameters.Signup_Method
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

fun Route.userRouting() {
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

    post("/signout") {
//        val username =
    }

    get("/{username}") {
        val username = call.parameters.get("username")
        if (username == null)
            call.respond(message = "null", status = HttpStatusCode.BadRequest).also { return@get }

        val user = userService.getUser(username!!)

        if (user == null)
            call.respond(message = "User is not found", status = HttpStatusCode.NotFound)
        else
            call.respond(message = user, status = HttpStatusCode.OK)
    }


    authenticate(AuthMethod.method) {
        put("/username") {
            val newUsername = call.receiveOrNull<Username>() ?: kotlin.run {
                call.respond(message = "username must be passed", status = HttpStatusCode.BadRequest)
                return@put
            }

            val user = userService.getUser(newUsername.username)
            if (user != null) {
                call.respond(message = "username is already used", status = HttpStatusCode.BadRequest)
                return@put
            }

            val principal = call.principal<JWTPrincipal>()
            val username = principal!!.payload.getClaim(AuthenticationParameters.USERNAME).asString()

            userService.updateUsername(
                oldUsername = username,
                newUsername = newUsername.username,
            ).let {
                if (it)
                    call.respond(message = "success", status = HttpStatusCode.OK)
                else
                    call.respond(message = "Cannot update username", status = HttpStatusCode.BadRequest)
            }
        }
    }


    authenticate(AuthMethod.method) {
        put("/name") {
            val newUsername = call.receiveOrNull<UpdateName>() ?: kotlin.run {
                call.respond(message = "username must be passed", status = HttpStatusCode.BadRequest)
                return@put
            }

            val username = Utils.getUsername(call)

            userService.updateName(
                username = username,
                name = newUsername.name,
            ).let {
                if (it)
                    call.respond(message = "success", status = HttpStatusCode.OK)
                else
                    call.respond(message = "Cannot update username", status = HttpStatusCode.BadRequest)
            }
        }
    }

}