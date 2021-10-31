package com.example

import com.example.request.PostRequest
import com.google.gson.GsonBuilder
import org.junit.Test

class JsonTest {

    @Test
    fun testPostJson() {
        println(
            GsonBuilder().create().toJsonTree(
                PostRequest(
                    caption = "hassan",
                    imageUrl = "sfsdf"
                )
            ).toString()
        )
    }
}