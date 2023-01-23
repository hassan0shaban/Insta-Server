package com.example.repo

import com.example.di.mainModule
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import org.koin.test.inject

class NotificationRepositoryImplTest : KoinTest {

    private val notificationRepository: NotificationRepository by inject()

    @Before
    fun setUp() {
        startKoin {
            loadKoinModules(mainModule)
        }
    }

    @Test
    fun insertNotification() = runBlockingTest {
        runBlocking {
            notificationRepository
                .getLikeNotifications(username = "5", limit = 50)
        }.let {
            it.onSuccess {
                it.forEach {
                    println(it.time)
                }
            }
            it.onFailure {
                it.printStackTrace()
            }
        }
    }

    @Test
    fun getLikeNotifications() = runBlockingTest {
        runBlocking {
            notificationRepository
                .getLikeNotifications(username = "5", limit = 50)
        }.onSuccess {
            it.forEach {
                println(it.time)
            }
        }.onFailure {
            it.printStackTrace()
        }
    }

    @Test
    fun getCommentNotifications() = runBlockingTest {
        runBlocking {
            notificationRepository
                .getCommentNotifications(username = "5", limit = 50)
        }.onSuccess {
            it.forEach {
                println(it)
            }
        }.onFailure {
            it.printStackTrace()
        }
    }
}