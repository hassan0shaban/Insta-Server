package com.example.repo

import com.example.di.databaseModule
import com.example.di.mainModule
import com.example.di.serviceModule
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import org.koin.test.inject

class SearchRepositoryImplTest : KoinTest {

    private val searchRepository: SearchRepository by inject()

    @Before
    fun setUp() {
        startKoin {
            modules(
                serviceModule,
                mainModule,
                databaseModule
            )
        }
    }

    @Test
    fun searchPosts() = runBlockingTest {
        runBlocking {
            searchRepository.searchPosts("cap")
        }.let {
            it.onFailure {
                it.printStackTrace()
            }
            it.onSuccess {
                Truth.assertThat(it.size).isEqualTo(8)
            }
            Truth.assertThat(it.isSuccess).isEqualTo(true)
        }
    }
}