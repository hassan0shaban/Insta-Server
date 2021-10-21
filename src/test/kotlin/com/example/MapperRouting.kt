package com.example

import com.example.model.User
import io.ktor.http.*
import io.ktor.application.*
import kotlin.test.*
import io.ktor.server.testing.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement

class MapperRouting {
    @Test
    fun testRoot() {
        withTestApplication(Application::module) {
            handleRequest(HttpMethod.Post, "/users/create") {
                setBody(
                    Json.encodeToJsonElement(
                        User(
                            name = "hassan",
                            bio = "",
                            email = "",
                            phone_number = "",
                            user_image_url = "",
                            uid = null,
                            username = "",
                        )
                    ).toString().also { println(it) }
                )
            }.apply {
                assertNotEquals(HttpStatusCode.BadRequest, response.status())
            }
        }
    }
}