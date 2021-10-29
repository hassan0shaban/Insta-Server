package com.example.dp.table

import com.example.dp.utils.CommentFields
import com.example.dp.utils.TableNames
import org.jetbrains.exposed.sql.Table

object CommentTable : Table(TableNames.Post) {
    val pid = integer(CommentFields.pid) references (PostTable.pid).autoIncrement()
    val uid = integer(CommentFields.uid) references (UserTable.uid).autoIncrement()
    val commentContent = varchar(CommentFields.commentContent, 120)
    val time = datetime(CommentFields.time)
}

