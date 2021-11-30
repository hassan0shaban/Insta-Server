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

class SearchServiceTest : KoinTest {

    private val searchService: SearchService by inject()

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
    fun searchPosts() {
            searchService
                .searchPosts("cap")
                .let {
                    Truth.assertThat(it.size).isEqualTo(8)
                }
    }
}