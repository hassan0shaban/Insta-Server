package com.example.repo

import com.example.model.Notification
import com.example.response.CommentNotification
import com.example.response.LikeNotification
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.statements.InsertStatement

interface NotificationRepository {
    fun getLikeNotifications(username: String, limit: Int): Result<ArrayList<LikeNotification>>
    fun getCommentNotifications(username: String, limit: Int): Result<ArrayList<CommentNotification>>
}