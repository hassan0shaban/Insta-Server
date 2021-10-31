package com.example.dp.table

import com.example.dp.utils.ConnectionFields
import com.example.dp.utils.TableNames
import org.jetbrains.exposed.sql.Table

object FollowerTable : Table(TableNames.Connection) {
    val followerUid = varchar(ConnectionFields.followerUid, 80) references (UserTable.username)
    val username = varchar(ConnectionFields.uid, 80) references (UserTable.username)
    val time = datetime(ConnectionFields.time)
}
