package com.example.service

import com.example.repo.MessageRepository
import com.example.request.MessageInsertRequest
import com.example.response.MessageResponse

class MessageService(
    private val messageRepository: MessageRepository
) {
    fun insertMessage(request: MessageInsertRequest, username: String): Boolean =
        messageRepository
            .insertMessage(request, username)
            .getOrElse {
                it.printStackTrace()
                return@getOrElse false
            }.let {
                return@let true
            }

    fun deleteMessage(messageId: Int, username: String): Boolean =
        messageRepository
            .deleteMessage(messageId, username)
            .getOrElse {
                it.printStackTrace()
                return@getOrElse false
            } == 1

    fun getMessage(messageId: Int, username: String): MessageResponse? =
        messageRepository
            .getMessage(messageId, username)
            .getOrElse {
                it.printStackTrace()
                return@getOrElse null
            }

    fun getMessages(username: String, connection: String): List<MessageResponse> =
        messageRepository
            .getMessages(username, connection)
            .getOrElse {
                it.printStackTrace()
                return@getOrElse emptyList()
            }
            .let {
                return@let it
            }
}

