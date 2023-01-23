package com.example.route

import com.example.AuthMethod
import com.example.maper.CustomPageable
import com.example.route.AuthenticationParameters.USERNAME
import com.example.service.PostService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.feedRouting() {

    val postService: PostService by inject()

    authenticate(AuthMethod.method) {
        get("/feed") {
            val page = call.parameters.get("page")?.toInt().takeIf { (it ?: 0) > 0 } ?: 1
            val pageSize = call.parameters.get("page-size")?.toInt().takeIf { (it ?: 0) > 0 } ?: 25
            val principal = call.principal<JWTPrincipal>()
            val username = principal!!.payload.getClaim(USERNAME).asString()

            val posts = postService.getFeedPosts(username, page, pageSize)

            call.respond(CustomPageable.pageable(page, pageSize, posts))
        }
    }
}