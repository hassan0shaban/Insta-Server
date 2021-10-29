package com.example.maper

import com.example.dp.table.CommentTable
import com.example.dp.table.ConnectionTable
import com.example.dp.table.LikeTable
import com.example.dp.table.UserTable
import com.example.model.Comment
import com.example.model.Connection
import com.example.model.Like
import com.example.model.User
import org.jetbrains.exposed.sql.ResultRow

object Mapper {
    fun userFromResultRow(resultRow: ResultRow): User {
        return User(
            name = resultRow[UserTable.name],
            password = resultRow[UserTable.password],
            bio = resultRow[UserTable.bio],
            username = resultRow[UserTable.userName],
            uid = resultRow[UserTable.uid],
            user_image_url = resultRow[UserTable.user_image_url],
            email = resultRow[UserTable.email],
            phone_number = resultRow[UserTable.phoneNumber],
        )
    }

    fun getConnectionFromResultRow(resultRow: ResultRow): Connection =
        Connection(
            followerUid = resultRow[ConnectionTable.followerUid],
            uid = resultRow[ConnectionTable.uid],
            time = resultRow[ConnectionTable.time],
        )

    fun getCommentsFromResultRow(resultRow: ResultRow): Comment =
        Comment(
            uid = resultRow[CommentTable.uid],
            time = resultRow[CommentTable.time],
            content = resultRow[CommentTable.commentContent],
            pid = resultRow[CommentTable.pid]
        )

    fun getLikesFromResultRow(resultRow: ResultRow): Like =
        Like(
            uid = resultRow[LikeTable.uid],
            time = resultRow[LikeTable.time],
            pid = resultRow[LikeTable.pid]
        )
}