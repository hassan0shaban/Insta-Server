package com.example.dp.table

import com.example.dp.utils.ConnectionFields
import com.example.dp.utils.TableNames
import org.jetbrains.exposed.sql.Table

object ConnectionTable : Table(TableNames.Connection) {
    val followerUid = varchar(ConnectionFields.followerUid, 80) references (UserTable.userName)
    val uid = varchar(ConnectionFields.uid, 80) references (UserTable.userName)
    val time = datetime(ConnectionFields.time)
}

