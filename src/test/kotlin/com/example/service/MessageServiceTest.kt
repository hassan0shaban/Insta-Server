package com.example.service

import com.example.di.mainModule
import com.example.request.MessageInsertRequest
import com.google.common.truth.Truth
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import org.koin.test.inject

class MessageServiceTest : KoinTest {

    private val messageService: MessageService by inject()

    @Before
    fun setUp() {
        startKoin {
            modules(
                mainModule
            )
        }
    }

    @After
    fun tearDown() {
    }

    @Test
    fun insertMessage() {
        Truth.assertThat(
            messageService
                .insertMessage(MessageInsertRequest(receiver = "5", message = "123"), "2")
        ).isTrue()
    }

    @Test
    fun deleteMessage() {
        Truth.assertThat(
            messageService
                .deleteMessage(37, "2")
        ).isTrue()
    }

    @Test
    fun getMessage() {
        Truth.assertThat(
            messageService
                .getMessage(35, username = "1")
        ).isNotNull()
    }

    @Test
    fun getMessages() {
        Truth.assertThat(
            messageService
                .getMessages( username = "5", connection = "1").size
        ).isEqualTo(20)
    }
}