package com.example.time

import com.example.utils.TimeFormatter
import org.joda.time.DateTime
import org.joda.time.DateTimeFieldType
import org.junit.Test

class TimeFormatterTest {

    @Test
    fun `time formatter from date to string full date time`() {
        println(DateTime.now().millis.toString())

        throw Throwable(
            message = TimeFormatter.stringToDateTime("02021.November.04 AD 02:22 PM").millis.toString()
        )
    }

    @Test
    fun `print time`() {
        val now = DateTime()
        val dateTime = TimeFormatter.stringToDateTime(time = ("02021.November.03 AD 08:01 PM"))
        println(((now.millis - dateTime.millis) / 1000))
    }

    @Test
    fun `time formatter from string to date full date time`() {
        throw Throwable(
            message =  TimeFormatter.dateTimeToString(DateTime())
        )
    }
}
