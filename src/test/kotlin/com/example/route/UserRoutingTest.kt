package com.example.route

import com.example.di.databaseModule
import com.example.di.serviceModule
import com.example.request.CreateUserByEmailRequest
import com.example.service.UserService
import com.google.common.truth.Truth.assertThat
import com.google.gson.GsonBuilder
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.routing.*
import io.ktor.server.testing.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import org.koin.test.inject

class UserRoutingTest : KoinTest {

    private val userService: UserService by inject()

    @Before
    fun setUp() {
        startKoin {
            modules(
                serviceModule,
                databaseModule
            )
        }
    }

    @Test
    fun printJsonElem() {
        println(

        )
    }

    @Test
    fun `empty password should return Bad Request`() {
        withTestApplication(
            moduleFunction = {
                install(ContentNegotiation) { gson { } }
            }
        ) {
            handleRequest(HttpMethod.Post, "users/create") {
                setBody(
                    """
                        {
                        "password" : "hassan",
                        "email" : "hassan.shaba@gmail.com"
                        }
                    """
                ).toString().also { println(it) }
            }.apply {
                assertThat(response.status()).isEqualTo(HttpStatusCode.OK)
                println(response.content)
            }
        }
    }

    @Test
    fun `empty email should return Bad Request`() {
        withTestApplication(
            moduleFunction = {
//                install(Routing) { userRouting() }
                install(ContentNegotiation) { gson { } }
            }
        ) {
            handleRequest(HttpMethod.Post, "users/create") {
                setBody(
//                    """
//                        {"email" : "hassan", "password" : "123456789" }
//                    """
                    GsonBuilder().create().toJsonTree(
                        CreateUserByEmailRequest(
                            email = "hassan",
                            password = "12345678",
                            name = ""
                        )
                    ).toString().also { println(it) }
                )
            }.apply {
                assertThat(response.status()).isEqualTo(HttpStatusCode.OK)
                println(response.content)
            }
        }
    }
}