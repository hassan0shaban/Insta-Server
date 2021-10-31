package com.example.maper

import com.example.dp.table.PostTable
import com.example.dp.table.UserTable
import com.example.model.Post
import com.example.model.User
import com.example.utils.TimeFormat
import org.jetbrains.exposed.sql.ResultRow

object PostMapper {

    fun postFromResultRow(resultRow: ResultRow): Post =
        Post(
            pid = resultRow[PostTable.pid],
            time = resultRow[PostTable.time].let { TimeFormat.dateTimeToString(it)},
            caption = resultRow[PostTable.caption],
            imageUrl = resultRow[PostTable.imageUrl],
            username = resultRow[PostTable.username],
        )
}