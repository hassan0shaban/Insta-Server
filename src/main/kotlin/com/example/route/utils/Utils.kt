package com.example.route

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.route.utils.Constants.`30 sec`
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
}