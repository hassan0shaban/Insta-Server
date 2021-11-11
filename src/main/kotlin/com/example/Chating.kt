package com.example

import com.example.route.Utils
import com.google.gson.Gson
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.AutoHeadResponse
import io.ktor.routing.*
import io.ktor.websocket.*
import io.ktor.http.cio.websocket.*
import io.ktor.http.content.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import java.io.File
import java.time.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.LinkedHashSet


abstract class Event(val type: String)
data class ChatMessage(val message: String = "", val sender: String = "", val receiver: String = "") : Event("message")
data class JoinChat(val previousMessages: ArrayList<ChatMessage>, val author: String) : Event("joinChat")
data class UpdateParticipants(val participants: Set<String>) : Event("participantsUpdate")

val animals = File("animals.txt").readLines()
val colors = File("colors.txt").readLines()

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    /* // Uncomment this if you are doing local development
    install(CORS) {
        header(HttpHeaders.AccessControlAllowOrigin)
        anyHost()
    }
    */


}

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)
