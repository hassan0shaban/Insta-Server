package com.example.repo

import com.example.di.mainModule
import com.example.request.MessageInsertRequest
import com.google.common.truth.Truth.assertThat
import org.jetbrains.exposed.sql.Database
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import org.koin.test.inject

class MessageRepositoryImplTest : KoinTest {

    private val messageRepository : MessageRepository by inject()
    private val database : Database by inject()

    @Before
    fun setUp() {
        startKoin {
            modules(
                mainModule
            )
        }
        println(database.version)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun insertMessage() {
        for (i in 1..10)
        assertThat(
            messageRepository.insertMessage(MessageInsertRequest(message = "1", receiver = "1"), "5").isSuccess
        ).isTrue()
    }

    @Test
    fun deleteMessage() {
        assertThat(
            messageRepository.deleteMessage(12, username = "1").getOrThrow()
        ).isEqualTo(1)
    }

    @Test
    fun getMessage() {
        assertThat(
            messageRepository.getMessage(13, "1").getOrThrow()?.message
        ).isEqualTo("1")
    }

    @Test
    fun getMessages() {
        assertThat(
            messageRepository.getMessages(username = "1", connection = "5").getOrThrow().size
        ).isEqualTo(20)
    }

    @Test
    fun getChats() {
        println(messageRepository.getChats(username = "1").getOrThrow().toMutableList())

        assertThat(
            messageRepository.getChats(username = "1").getOrThrow().size
        ).isEqualTo(4)
    }
}