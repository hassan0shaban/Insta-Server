package com.example.dp.table

import com.example.dp.utils.LikeFields
import com.example.dp.utils.TableNames
import org.jetbrains.exposed.sql.Table

object LikeTable : Table(TableNames.Like) {
    val pid = integer(LikeFields.pid) references (PostTable.pid)
    val uid = integer(LikeFields.uid) references (UserTable.uid)
    val time = datetime(LikeFields.time)
}

