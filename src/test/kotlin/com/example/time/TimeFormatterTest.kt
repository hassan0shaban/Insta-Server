package com.example.time

import com.example.utils.TimeFormat
import org.joda.time.DateTime
import org.joda.time.DateTimeFieldType
import org.junit.Test

class TimeFormatterTest {

    @Test
    fun `time formatter from date to string full date time`() {
        throw Throwable(
            message = TimeFormat.stringToDateTime("02021.October.31 AD 08:48 AM")
                .get(
                    DateTimeFieldType.dayOfMonth()
                ).toString()
        )
    }

    @Test
    fun `time formatter from string to date full date time`() {
        throw Throwable(
            message =  TimeFormat.dateTimeToString(DateTime())
        )
    }
}
