package com.example.service

import com.example.repo.NotificationRepository
import com.example.response.Notification
import kotlin.math.min

class NotificationsService(
    private val notificationRepository: NotificationRepository
) {

    fun getNotifications(username: String, page: Int = 1, pageSize: Int = 20): List<Notification> {
        val notifications = mutableListOf<Notification>()

        notificationRepository.getLikeNotifications(username, limit = pageSize).also {
            notifications += it
                .getOrElse {
                    it.printStackTrace()
                    emptyList()
                }

        }
        notificationRepository.getCommentNotifications(username, limit = pageSize).also {
            notifications += it
                .getOrElse {
                    it.printStackTrace()
                    emptyList()
                }
        }

        return notifications.sortedByDescending { it.time }.subList(0, min(pageSize, notifications.size))
    }
}