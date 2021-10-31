package com.example.maper

import com.example.dp.table.*
import com.example.model.*
import com.example.utils.TimeFormat
import org.jetbrains.exposed.sql.ResultRow

object Mapper {
    fun userFromResultRow(resultRow: ResultRow): User {
        return User(
            name = resultRow[UserTable.name]!!,
            password = resultRow[UserTable.password],
            bio = resultRow[UserTable.bio],
            username = resultRow[UserTable.username],
            uid = resultRow[UserTable.uid],
            userImageUrl = resultRow[UserTable.user_image_url],
            email = resultRow[UserTable.email],
            phoneNumber = resultRow[UserTable.phoneNumber],
        )
    }

    fun geFollowerFromResultRow(resultRow: ResultRow): Follower =
        Follower(
            followerUid = resultRow[FollowerTable.followerUid],
            username = resultRow[FollowerTable.username],
            time = resultRow[FollowerTable.time].let { TimeFormat.dateTimeToString(it) },
        )

    fun getCommentsFromResultRow(resultRow: ResultRow): Comment =
        Comment(
            username = resultRow[CommentTable.username],
            time = resultRow[CommentTable.time],
            content = resultRow[CommentTable.commentContent],
            pid = resultRow[CommentTable.pid]
        )

    fun getLikesFromResultRow(resultRow: ResultRow): Like =
        Like(
            username = resultRow[LikeTable.username],
            time = resultRow[LikeTable.time],
            pid = resultRow[LikeTable.pid]
        )

    fun followRequestFromResultRow(resultRow: ResultRow)  =
        FollowRequest(
            username = resultRow[FollowRequestTable.username],
            followerUsername= resultRow[FollowRequestTable.followerUsername],
            time = resultRow[FollowRequestTable.time].let { TimeFormat.dateTimeToString(it) },
        )

    fun followerFromResultRow(resultRow: ResultRow) =
        Follower(
            username = resultRow[FollowerTable.username],
            followerUid= resultRow[FollowerTable.followerUid],
            time = resultRow[FollowerTable.time].let { TimeFormat.dateTimeToString(it) },
        )
}