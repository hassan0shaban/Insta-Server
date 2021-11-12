package com.example.route

import com.example.response.ChatResponse
import com.google.common.truth.Truth
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.server.testing.*
import io.ktor.util.*
import io.ktor.util.Identity.encode
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.koin.core.Koin
import org.koin.test.KoinTest
import org.koin.test.get

class MessageRoutingKtTest : KoinTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun messageRouting() {
        withTestApplication(
            moduleFunction = {
                install(ContentNegotiation) { gson { } }
            }
        ) {
            handleRequest(HttpMethod.Get, "/chats",) {
                headersOf(
                    HttpHeaders.Authorization,
                    "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJodHRwOi8vMC4wLjAuMDo4MDgwL2hlbGxvIiwiaXNzIjoiaHR0cDovLzAuMC4wLjA6ODA4MC8iLCJleHAiOjE2Mzg2MDA1NjQsInVzZXJuYW1lIjoiMSJ9.PV1isIjteTstHmkectYQSikXVeYF4UNny9TZuO2Z4gM"
                )
            }.let {
                Truth.assertThat(it.response.status()).isEqualTo(HttpStatusCode.OK)
            }
        }
    }
}