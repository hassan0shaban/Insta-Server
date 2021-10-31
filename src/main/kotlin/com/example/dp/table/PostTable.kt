package com.example.dp.table

import com.example.dp.utils.PostFields
import com.example.dp.utils.TableNames
import org.jetbrains.exposed.sql.Table

object PostTable : Table(TableNames.Post) {
    val pid = integer(PostFields.pid).primaryKey().autoIncrement()
    val username = varchar(PostFields.uid, 80) references (UserTable.username)
    val caption = varchar(PostFields.caption, 320)
    val imageUrl = varchar(PostFields.imageUrl, 320)
    val time = datetime(PostFields.time)
}
