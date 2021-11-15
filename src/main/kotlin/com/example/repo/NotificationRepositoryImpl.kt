package com.example.repo

import com.example.maper.Mapper
import com.example.response.CommentNotification
import com.example.response.LikeNotification
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction

class NotificationRepositoryImpl(
    private val database: Database
) : NotificationRepository {

    override fun getLikeNotifications(username: String, limit: Int): Result<ArrayList<LikeNotification>> = kotlin.runCatching {
        transaction {
            val conn = TransactionManager.current().connection
            val statement = conn.createStatement()
            val query = "SELECT name, post.image_url, post.pid, (count(`like`.username) -1) as count, max(`like`.time) as time\n" +
                    "from social_media.like\n" +
                    "         join user\n" +
                    "         join post\n" +
                    "where (\n" +
                    "    user.username = `like`.username and\n" +
                    "       post.pid = `like`.pid and\n" +
                    "       post.username = '$username'\n" +
                    "    )\n" +
                    "group by post.pid\n" +
                    "limit $limit;"

            statement.executeQuery(query).let {
                Mapper.mapToLikeNotifications(it)
            }
        }
    }

    override fun getCommentNotifications(username: String, limit: Int): Result<ArrayList<CommentNotification>> = kotlin.runCatching {
        transaction {
            val conn = TransactionManager.current().connection
            val statement = conn.createStatement()
            val query =
                "SELECT name, post.image_url, post.pid, (count(comment.username) -1) as count, max(comment.time) as time\n" +
                        "from social_media.comment\n" +
                        "         join user\n" +
                        "         join post\n" +
                        "where (\n" +
                        "    user.username = comment.username and\n" +
                        "       post.pid = comment.pid and\n" +
                        "       post.username = '$username'\n" +
                        "    )\n" +
                        "group by post.pid\n" +
                        "limit $limit;"

            statement.executeQuery(query).let {
                Mapper.mapToCommentNotifications(it)
            }
        }
    }
}
