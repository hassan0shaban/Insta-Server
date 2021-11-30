package com.example.db.table

import com.example.db.utils.PostFields
import com.example.db.utils.TableNames
import org.jetbrains.exposed.sql.Table

object PostTable : Table(TableNames.Post) {
    val pid = integer(PostFields.pid).primaryKey().autoIncrement()
    val type = integer(PostFields.type)
    val username = varchar(PostFields.username, 80) references (UserTable.username)
    val caption = varchar(PostFields.caption, 320)
    val imageUrl = varchar(PostFields.imageUrl, 320)
    val time = datetime(PostFields.time)
}
