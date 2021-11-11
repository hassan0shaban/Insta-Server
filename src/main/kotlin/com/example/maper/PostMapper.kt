package com.example.maper

import com.example.dp.table.PostTable
import com.example.model.Post
import com.example.utils.TimeFormatter
import org.jetbrains.exposed.sql.ResultRow

object PostMapper {

    fun postFromResultRow(resultRow: ResultRow): Post =
        Post(
            postId = resultRow[PostTable.pid],
            time = resultRow[PostTable.time].let { TimeFormatter.dateTimeToString(it)},
            caption = resultRow[PostTable.caption],
//            TODO change url
            postImageUrl = "http://192.168.1.15:8080/" + resultRow[PostTable.imageUrl],
            username = resultRow[PostTable.username],
        )
}