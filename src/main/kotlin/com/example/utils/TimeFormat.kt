package com.example.utils

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

object TimeFormat {
    private const val TIME_PATTERN = "yyyyy.MMMMM.dd GGG hh:mm aaa"

    fun stringToDateTime(time: String): DateTime =
        DateTimeFormat.forPattern(TIME_PATTERN)
            .parseDateTime(time)

    fun dateTimeToString(time: DateTime): String =
        DateTimeFormat.forPattern(TIME_PATTERN)
            .print(time)
}