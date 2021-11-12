package com.example.utils

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

object TimeFormatter {
    private const val TIME_PATTERN = "yyyy.MMM.dd GGG hh:mm aaa"

    fun stringToDateTime(time: String): DateTime =
        DateTime.parse(time)

    fun dateTimeToString(time: DateTime): String =
        time.toString()
}
