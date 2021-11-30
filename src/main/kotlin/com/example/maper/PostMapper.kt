package com.example.maper

import com.example.db.table.PostTable
import com.example.db.table.UserTable
import com.example.model.Post
import com.example.response.SearchedPost
import com.example.utils.TimeFormatter
import org.jetbrains.exposed.sql.ResultRow

object PostMapper {

    fun postFromResultRow(resultRow: ResultRow): Post =
        Post(
            postId = resultRow[PostTable.pid],
            type = resultRow[PostTable.type],
            time = resultRow[PostTable.time].let { TimeFormatter.dateTimeToString(it)},
            caption = resultRow[PostTable.caption],
//            TODO change url
            postImageUrl = "http://192.168.1.15:5000/" + resultRow[PostTable.imageUrl],
            username = resultRow[PostTable.username],
        )

    fun mapToSearchedPost(resultRow: ResultRow) : SearchedPost =
        SearchedPost(
            postId = resultRow[PostTable.pid],
            time = resultRow[PostTable.time].let { TimeFormatter.dateTimeToString(it)},
            caption = resultRow[PostTable.caption],
            postImageUrl = "http://192.168.1.15:5000/" + resultRow[PostTable.imageUrl],
            username = resultRow[PostTable.username],
            userImageUrl = resultRow[UserTable.userImageUrl],
            profileName = resultRow[UserTable.name]
        )
}