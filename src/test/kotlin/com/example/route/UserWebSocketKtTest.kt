package com.example.route;

import com.example.module
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test

public class UserWebSocketKtTest {

    @Test
    fun testPostEmaillogin() {
        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Post, "/email-login").apply {


            }
        }
    }
}