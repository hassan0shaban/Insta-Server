package com.example.route

import com.example.AuthMethod
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.cio.websocket.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.websocket.*
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

fun Route.webSocket() {

    val connections = Collections.synchronizedMap<String, ArrayList<Connection>>(HashMap())

    authenticate(AuthMethod.method) {
        webSocket("/chat") {
            val username = Utils.getUsername(call)
            if (!connections.containsKey(username)) {
                connections.set(username, arrayListOf())
            }

            connections.get(username)?.add(Connection(this))

            for (frame in incoming) {
                when (frame) {
                    is Frame.Text -> {
                        val receivedText = frame.readText()

                        connections.get(username)?.forEach {
                            it.session.send(receivedText)
                        }
                    }
                }
            }
        }
    }

    get("/get") {
        call.respond("hksfhkshfsahlf")
    }
}

class Connection(val session: DefaultWebSocketSession) {
    companion object {
        var lastId = AtomicInteger(0)
    }

    val name = "user${lastId.getAndIncrement()}"
}
