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
                Truth.assertThat(it.size).isEqualTo(2)
                it.forEach {
                    println(it)
                }
            }
            it.onFailure {
                it.printStackTrace()
            }
        }
    }
}