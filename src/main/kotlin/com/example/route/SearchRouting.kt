package com.example.route

import com.example.route.SearchParameter.SEARCH_PARAMETER_TEXT
import com.example.service.SearchService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

object SearchParameter {
    const val SEARCH_PARAMETER_TEXT = "text"
}


fun Route.search() {
    val searchService: SearchService by inject()

    get("search/posts") {
        val text = call.parameters.get(SEARCH_PARAMETER_TEXT) ?: kotlin.run {
            call.respond(status = HttpStatusCode.BadRequest, "must pass text content")
            return@get
        }

        call.respond(
            status = HttpStatusCode.OK,
            message = searchService.searchPosts(text = text)
        )
    }
}