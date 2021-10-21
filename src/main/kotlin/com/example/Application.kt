package com.example

import com.example.di.mainModule
import com.example.route.userRouting
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.Database
import org.koin.ktor.ext.Koin

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(ContentNegotiation) { gson { } }
    install(Koin) { modules(mainModule) }

    routing {
        userRouting()
    }
}