package com.example.maper

import com.example.db.table.*
import com.example.model.*
import com.example.response.*
import com.example.response.utils.Utils.COMMENT_NOTIFICATION_TYPE
import com.example.response.utils.Utils.LIKE_NOTIFICATION_TYPE
import com.example.utils.TimeFormatter
import org.jetbrains.exposed.sql.ResultRow
import java.sql.ResultSet

object Mapper {
    fun userFromResultRow(resultRow: ResultRow): User {
        return User(
            name = resultRow[UserTable.name],
            password = resultRow[UserTable.password],
            bio = resultRow[UserTable.bio],
            username = resultRow[UserTable.username],
            uid = resultRow[UserTable.uid],
            //TODO change url
            userImageUrl = "http://192.168.1.15:5000/" + resultRow[UserTable.userImageUrl],
            email = resultRow[UserTable.email],
            phoneNumber = resultRow[UserTable.phoneNumber],
        )
    }

    fun geFollowerFromResultRow(resultRow: ResultRow): Connection =
        Connection(
            username = resultRow[UserTable.username],
            profileName = resultRow[UserTable.name],
            userImageUrl = resultRow[UserTable.userImageUrl],
        )

    fun getCommentsFromResultRow(resultRow: ResultRow): Comment =
        Comment(
            username = resultRow[CommentTable.username],
            userImageUrl = "http://192.168.1.15:5000/" + resultRow[UserTable.userImageUrl],
            profileName = resultRow[UserTable.name],
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
            userImageUrl = "http://192.168.1.15:5000/" + resultRow[UserTable.userImageUrl],
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
            userImageUrl = resultRow[UserTable.userImageUrl]?.let { "http://192.168.1.15:5000/$it" },
            name = resultRow[UserTable.name],
            lastMessage = resultRow[MessageTable.message],
        )

    fun mapToLikeNotifications(resultSet: ResultSet): ArrayList<LikeNotification> {
        val notificationsList = arrayListOf<LikeNotification>()

        while (resultSet.next()) {
            notificationsList.add(
                LikeNotification(
                    type = LIKE_NOTIFICATION_TYPE,
                    likeCount = resultSet.getInt("count"),
                    profileName = resultSet.getString("name"),
                    postImageUrl = "http://192.168.1.15:5000/" + resultSet.getString("image_url"),
                    time = resultSet.getObject("time").toString(),
                    postId = resultSet.getInt("pid"),
                )
            )
        }
        return notificationsList
    }

    fun mapToCommentNotifications(resultSet: ResultSet): ArrayList<CommentNotification> {
        val notificationsList = arrayListOf<CommentNotification>()

        while (resultSet.next()) {
            notificationsList.add(
                CommentNotification(
                    type = COMMENT_NOTIFICATION_TYPE,
                    commentCount = resultSet.getInt("count"),
                    profileName = resultSet.getString("name"),
                    postImageUrl = "http://192.168.1.15:5000/" + resultSet.getString("image_url"),
                    time = resultSet.getObject("time").toString(),
                    postId = resultSet.getInt("pid"),
                )
            )
        }
        return notificationsList
    }
}