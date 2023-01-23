package com.example.repo

import com.example.di.databaseModule
import com.example.di.serviceModule
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import org.koin.test.inject

class UserRepositoryImplTest : KoinTest {

    private val userRepository: UserRepository by inject()

    @Before
    fun setUp() {
        startKoin {
            modules(
                serviceModule,
                databaseModule
            )
        }
    }

    @After
    fun tearDown() {
    }

    @Test
    fun insertUser() {
//        userRepository.insertUser(User(name = "name", password = "")).let {
//            assertThat(it).isNotNull()
//        }
    }

    @Test
    fun getUser() {
        assertThat(true).isTrue()
    }


    @Test
    fun changePassword() {
    }

    @Test
    fun updateUsername() {
    }
}