package com.example.route

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import javax.security.auth.Subject

suspend fun ApplicationCall.redirectInternally(path: String) {
    val cp = object: RequestConnectionPoint by this.request.local {
        override val uri: String = path
    }
    val req = object: ApplicationRequest by this.request {
        override val local: RequestConnectionPoint = cp
    }
    val call = object: ApplicationCall by this {
        override val request: ApplicationRequest = req
    }

    this.application.execute(call, subject = Unit)
}