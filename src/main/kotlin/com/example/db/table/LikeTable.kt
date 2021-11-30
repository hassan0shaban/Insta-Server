package com.example.db.table

import com.example.db.utils.LikeFields
import com.example.db.utils.TableNames
import com.example.db.utils.UserFields
import org.jetbrains.exposed.sql.Table

object LikeTable : Table(TableNames.Like) {
    val pid = integer(LikeFields.pid) references (PostTable.pid)
    val username = varchar(UserFields.username, 80) references (UserTable.username)
    val time = datetime(LikeFields.time)
}
