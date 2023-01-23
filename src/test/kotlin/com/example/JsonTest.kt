package com.example

import com.example.request.PostRequest
import com.example.response.FacebookProfileResponse
import com.google.gson.Gson
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

    @Test
    fun testFacebookProfileGson() {
        println(
            Gson().fromJson(
                "{\"id\":\"1449443755408487\",\"name\":\"Hassan Harera\",\"email\":\"hassanstar201118\\u0040gmail.com\"}",
                FacebookProfileResponse::class.java
            )
        )
    }
}