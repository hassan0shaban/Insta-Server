package com.example.db.table

import com.example.db.utils.Fields.FOLLOWER_USERNAME
import com.example.db.utils.Fields.TIME
import com.example.db.utils.Fields.USERNAME
import com.example.db.utils.TableNames
import org.jetbrains.exposed.sql.Table

object FollowRequestTable : Table(TableNames.ConnectionRequest) {
    val followerUsername = varchar(FOLLOWER_USERNAME, 80) references UserTable.username
    val username = varchar(USERNAME, 80) references UserTable.username
    val time = datetime(TIME)
}