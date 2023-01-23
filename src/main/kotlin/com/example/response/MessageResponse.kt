package com.example.response

import com.example.utils.TimeFormatter
import org.joda.time.DateTime
import javax.sound.midi.Receiver

data class MessageResponse(
    val message: String,
    var messageId: Int,
    var sender: String,
    var receiver: String,
    var time: String,
)