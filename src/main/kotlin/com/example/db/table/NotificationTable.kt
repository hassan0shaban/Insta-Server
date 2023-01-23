package com.example.db.table

import com.example.db.utils.NotificationsFields
import com.example.db.utils.TableNames
import org.jetbrains.exposed.sql.Table

object NotificationTable : Table(TableNames.Notifications) {
    val pid = integer(NotificationsFields.pid) references (PostTable.pid)
    val username = varchar(NotificationsFields.username, 80) references (UserTable.username)
    val id = integer(NotificationsFields.notificationId).primaryKey().autoIncrement()
    val notificationType = integer(NotificationsFields.notificationType)
    val time = datetime(NotificationsFields.time)
}

