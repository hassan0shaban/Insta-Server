package com.example.dp.table

import com.example.dp.utils.MessageFields
import com.example.dp.utils.TableNames
import org.jetbrains.exposed.sql.Table

object MessageTable : Table(TableNames.Message) {
    val messageId = integer(MessageFields.messageId).primaryKey()
    val sender = varchar(MessageFields.sender, 80) references (UserTable.username)
    val receiver = varchar(MessageFields.receiver, 80) references (UserTable.username)
    val message = varchar(MessageFields.message, 300)
    val time = datetime(MessageFields.time)
}
