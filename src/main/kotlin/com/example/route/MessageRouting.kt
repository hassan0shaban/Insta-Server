package com.example.route

import com.example.AuthMethod
import com.example.request.MessageInsertRequest
import com.example.route.utils.Utils
import com.example.route.utils.Utils.getUsername
import com.example.service.MessageService
import com.google.gson.Gson
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import org.koin.ktor.ext.inject
import java.util.*

fun Route.messageRouting() {
    val messageService: MessageService by inject()

    authenticate(AuthMethod.method) {
        post("/message") {
            val request = call.receiveOrNull<MessageInsertRequest>() ?: kotlin.run {
                call.respond(message = "week info", status = HttpStatusCode.BadRequest)
                return@post
            }

            val username = Utils.getUsername(call)

            messageService.insertMessage(request, username).let {
                if (it)
                    call.respond(message = "success", status = HttpStatusCode.OK)
                else
                    call.respond(message = "wrong info", status = HttpStatusCode.NotImplemented)
            }
        }
    }

    authenticate(AuthMethod.method) {
        delete("/message/{messageId}") {
            val messageId = call.parameters.get("messageId") ?: kotlin.run {
                call.respond(message = "week info", status = HttpStatusCode.BadRequest)
                return@delete
            }

            val username = Utils.getUsername(call)

            messageService.deleteMessage(messageId.toInt(), username).let {
                if (it)
                    call.respond(message = "success", status = HttpStatusCode.OK)
                else
                    call.respond(message = "wrong info", status = HttpStatusCode.NotImplemented)
            }
        }
    }

    authenticate(AuthMethod.method) {
        get("/message/{messageId}") {
            val messageId = call.parameters.get("messageId") ?: kotlin.run {
                call.respond(message = "week info", status = HttpStatusCode.BadRequest)
                return@get
            }

            val username = Utils.getUsername(call)

            messageService.getMessage(messageId.toInt(), username).let { messageResponse ->
                if (messageResponse != null)
                    call.respond(message = messageResponse, status = HttpStatusCode.OK)
                else
                    call.respond(message = "wrong info", status = HttpStatusCode.NotImplemented)
            }
        }
    }

    authenticate(AuthMethod.method) {
        get("/chat/{username}") {
            val connection = call.parameters.get("username") ?: kotlin.run {
                call.respond(message = "week info", status = HttpStatusCode.BadRequest)
                return@get
            }

            val username = Utils.getUsername(call)

            messageService.getMessages(username, connection).let {
                call.respond(message = it, status = HttpStatusCode.OK)
            }
        }
    }

    authenticate(AuthMethod.method) {
        get("/chats") {
            val username = Utils.getUsername(call)

            messageService.getChats(username).let {
                call.respond(message = it, status = HttpStatusCode.OK)
            }
        }
    }

    val connections = Collections.synchronizedMap(HashMap<String, HashSet<DefaultWebSocketSession>>())

    authenticate(AuthMethod.method) {
        webSocket("/chat") {
            val username = getUsername(call = call)

            println("com.harera.insta D/USERNAME: $username")
            if (connections[username] == null)
                connections[username] = HashSet()

            connections[username]?.add(this)

            try {
                while (true) {
                    when (val frame = incoming.receive()) {
                        is Frame.Text -> {
                            val json = frame.readText()

                            val incomingMessage = Gson().fromJson(json, MessageInsertRequest::class.java) ?: continue

                            messageService.insertMessage(incomingMessage, username)
                            val messageResponse = messageService.getLastMessage(username, incomingMessage.receiver) ?: continue
                            val messageResponseJson = Gson().toJson(messageResponse)

                            connections[username]?.forEach { it.send(Frame.Text(messageResponseJson)) }
                            connections[incomingMessage.receiver]?.forEach { it.send(Frame.Text(messageResponseJson)) }
                        }
                    }
                }
            } catch (exception: ClosedReceiveChannelException) {
                println("A socket has left the chat!")
            } finally {
                connections[username]?.remove(this)
            }
        }
    }
}
