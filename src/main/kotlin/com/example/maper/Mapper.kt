package com.example.maper

import com.example.dp.table.*
import com.example.model.*
import com.example.response.ChatResponse
import com.example.response.LikeResponse
import com.example.response.MessageResponse
import com.example.utils.TimeFormatter
import org.jetbrains.exposed.sql.ResultRow

object Mapper {
    fun userFromResultRow(resultRow: ResultRow): User {
        return User(
            name = resultRow[UserTable.name]!!,
            password = resultRow[UserTable.password],
            bio = resultRow[UserTable.bio],
            username = resultRow[UserTable.username],
            uid = resultRow[UserTable.uid],
            //TODO change url
            userImageUrl = "http://192.168.1.15:8080/" + resultRow[UserTable.user_image_url],
            email = resultRow[UserTable.email],
            phoneNumber = resultRow[UserTable.phoneNumber],
        )
    }

    fun geFollowerFromResultRow(resultRow: ResultRow): Connection =
        Connection(
//            followerUid = resultRow[ConnectionsTable.user1],
            username = resultRow[UserTable.username],
//            time = resultRow[ConnectionsTable.time].let { TimeFormatter.dateTimeToString(it) },
            profileName = resultRow[UserTable.name],
            userImageUrl = resultRow[UserTable.user_image_url],
        )

    fun getCommentsFromResultRow(resultRow: ResultRow): Comment =
        Comment(
            username = resultRow[CommentTable.username],
            time = resultRow[CommentTable.time].let { TimeFormatter.dateTimeToString(it) },
            comment = resultRow[CommentTable.commentContent],
            postId = resultRow[CommentTable.pid],
            commentId = resultRow[CommentTable.commentId],
        )

    fun getLikeFromResultRow(resultRow: ResultRow): LikeResponse =
        LikeResponse(
            username = resultRow[LikeTable.username],
            time = resultRow[LikeTable.time].let { TimeFormatter.dateTimeToString(it) },
            postId = resultRow[LikeTable.pid]
        )

    fun followRequestFromResultRow(resultRow: ResultRow) =
        FollowRequest(
            username = resultRow[FollowRequestTable.username],
            followerUsername = resultRow[FollowRequestTable.followerUsername],
            time = resultRow[FollowRequestTable.time].let { TimeFormatter.dateTimeToString(it) },
        )

    fun connectionFromResultRow(resultRow: ResultRow) =
        Connection(
            username = resultRow[UserTable.username],
            profileName = resultRow[UserTable.name],
            userImageUrl = "http://192.168.1.15:8080/" + resultRow[UserTable.user_image_url],
        )

    fun messageFromResultRow(resultRow: ResultRow): MessageResponse =
        MessageResponse(
            messageId = resultRow[MessageTable.messageId],
            message = resultRow[MessageTable.message],
            time = resultRow[MessageTable.time].let { TimeFormatter.dateTimeToString(it) },
            sender = resultRow[MessageTable.sender],
            receiver = resultRow[MessageTable.receiver],
        )

    fun chatFromResultRow(resultRow: ResultRow) =
        ChatResponse(
            time = resultRow[MessageTable.time].let { TimeFormatter.dateTimeToString(it) },
            username = resultRow[UserTable.username],
            userImageUrl = resultRow[UserTable.user_image_url]?.let { "http://192.168.1.15:8080/$it" },
            name = resultRow[UserTable.name],
            lastMessage = resultRow[MessageTable.message],
        )
}