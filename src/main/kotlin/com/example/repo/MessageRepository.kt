package com.example.repo

import com.example.dp.table.MessageTable
import com.example.dp.table.UserTable
import com.example.maper.Mapper
import com.example.request.MessageInsertRequest
import com.example.response.ChatResponse
import com.example.response.MessageResponse
import com.mysql.cj.jdbc.ServerPreparedStatement
import com.mysql.cj.xdevapi.SelectStatementImpl
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import java.sql.PreparedStatement
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.SchemaUtils.drop
import org.jetbrains.exposed.sql.statements.Statement
import org.jetbrains.exposed.sql.transactions.TransactionManager


interface MessageRepository {
    fun insertMessage(request: MessageInsertRequest, username: String): Result<InsertStatement<Number>>
    fun deleteMessage(messageId: Int, username: String): Result<Int>
    fun getMessage(messageId: Int, username: String): Result<MessageResponse?>
    fun getMessages(username: String, connection: String): Result<List<MessageResponse>>
    fun getChats(username: String): Result<List<ChatResponse>>
}

class MessageRepositoryImpl(private val database: Database) : MessageRepository {

    override fun insertMessage(request: MessageInsertRequest, username: String) = kotlin.runCatching {
        transaction {
            MessageTable.insert {
                it[message] = request.message
                it[receiver] = request.receiver
                it[sender] = username
                it[time] = DateTime()
            }
        }
    }

    override fun deleteMessage(messageId: Int, username: String): Result<Int> = kotlin.runCatching {
        transaction {
            MessageTable
                .deleteWhere {
                    (MessageTable.messageId eq messageId) and
                            (MessageTable.sender eq username)
                }
        }
    }

    override fun getMessages(username: String, connection: String) = kotlin.runCatching {
        transaction {
            MessageTable
                .select {
                    ((MessageTable.sender eq username) and (MessageTable.receiver eq connection)) or
                            ((MessageTable.receiver eq username) and (MessageTable.sender eq connection))
                }
                .sortedBy { MessageTable.time }
                .map {
                    Mapper.messageFromResultRow(it)
                }
        }
    }

    override fun getMessage(messageId: Int, username: String): Result<MessageResponse?> = kotlin.runCatching {
        transaction {
            MessageTable
                .select {
                    ((MessageTable.messageId eq messageId) and (MessageTable.receiver eq username)) or
                            ((MessageTable.messageId eq messageId) and (MessageTable.sender eq username))
                }
                .firstOrNull()
                ?.let {
                    Mapper.messageFromResultRow(it)
                }
        }
    }

    override fun getChats(username: String): Result<List<ChatResponse>> = kotlin.runCatching {
        transaction {
            MessageTable
                .innerJoin(UserTable)
//            , { MessageTable.sender }, { UserTable.username })
//                .fullJoin(UserTable, { MessageTable.receiver }, { UserTable.username })
                .select {
                    (MessageTable.sender eq username) or
                            (MessageTable.receiver eq username)
                }
                .orderBy(MessageTable.time, SortOrder.DESC)
                .groupBy(UserTable.username)
                .map {
                    Mapper.chatFromResultRow(it)
                }
        }
    }
}

