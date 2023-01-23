package com.example.repo

import com.example.request.MessageInsertRequest
import com.example.response.ChatResponse
import com.example.response.MessageResponse
import org.jetbrains.exposed.sql.statements.InsertStatement


interface MessageRepository {
    fun insertMessage(request: MessageInsertRequest, username: String): Result<InsertStatement<Number>>
    fun deleteMessage(messageId: Int, username: String): Result<Int>
    fun getMessage(messageId: Int, username: String): Result<MessageResponse?>
    fun getMessages(username: String, connection: String): Result<List<MessageResponse>>
    fun getChats(username: String): Result<List<ChatResponse>>
    fun insertMessage(request: MessageInsertRequest): Result<InsertStatement<Number>>
    fun getLastMessage(username: String, connection: String): Result<MessageResponse?>
}