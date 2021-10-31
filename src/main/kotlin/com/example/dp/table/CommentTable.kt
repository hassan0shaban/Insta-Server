package com.example.dp.table

import com.example.dp.utils.CommentFields
import com.example.dp.utils.TableNames
import org.jetbrains.exposed.sql.Table
import org.joda.time.DateTime

object CommentTable : Table(TableNames.Comment) {
    val pid = integer(CommentFields.pid) references (PostTable.pid)
    val username = varchar(CommentFields.uid, 80) references (UserTable.username)
    val commentContent = varchar(CommentFields.commentContent, 120)
    val time = datetime(CommentFields.time)
}

