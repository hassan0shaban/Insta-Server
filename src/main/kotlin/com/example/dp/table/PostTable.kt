package com.harera.insta.data.dp.table

import com.example.dp.table.UserTable
import com.harera.insta.data.dp.utils.PostFields
import com.harera.insta.data.dp.utils.TableNames
import org.jetbrains.exposed.sql.Table

object PostTable : Table(TableNames.Post) {
    val pid = integer(PostFields.pid).primaryKey().autoIncrement()
    val uid = integer(PostFields.uid) references(UserTable.uid).autoIncrement()
    val caption = varchar(PostFields.caption, 320)
    val time = datetime(PostFields.time)
}

