package com.example

import com.example.request.PostRequest
import com.google.gson.GsonBuilder
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.tz.UTCProvider
import org.junit.Test
import java.sql.Timestamp
import java.time.Instant
import java.util.*

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