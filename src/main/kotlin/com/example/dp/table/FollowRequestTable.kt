package com.example.dp.table

import com.example.dp.utils.Fields
import com.example.dp.utils.Fields.FOLLOWER_USERNAME
import com.example.dp.utils.Fields.TIME
import com.example.dp.utils.Fields.USERNAME
import com.example.dp.utils.TableNames
import org.jetbrains.exposed.sql.Table

object FollowRequestTable : Table(TableNames.ConnectionRequest) {
    val followerUsername = varchar(FOLLOWER_USERNAME, 80) references UserTable.username
    val username = varchar(USERNAME, 80) references UserTable.username
    val time = datetime(TIME)
}