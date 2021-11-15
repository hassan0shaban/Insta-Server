package com.example.service

import com.example.di.mainModule
import com.google.common.truth.Truth
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import org.koin.test.inject

class NotificationsServiceTest : KoinTest {

    private val notificationsService: NotificationsService by inject()

    @Before
    fun setUp() {
        startKoin {
            loadKoinModules(
                mainModule
            )
        }
    }

    @Test
    fun getNotifications() = runBlockingTest {
        notificationsService
            .getNotifications("5")
            .let {
                Truth.assertThat(it.size).isEqualTo(3)
            }
    }
}