package com.example.db.table

import com.example.db.utils.MessageFields
import com.example.db.utils.TableNames
import org.jetbrains.exposed.sql.Table

object MessageTable : Table(TableNames.Message) {
    val messageId = integer(MessageFields.messageId).primaryKey()
    val sender = varchar(MessageFields.sender, 80) references (UserTable.username)
    val receiver = varchar(MessageFields.receiver, 80) references (UserTable.username)
    val message = varchar(MessageFields.message, 300)
    val time = datetime(MessageFields.time)
}
