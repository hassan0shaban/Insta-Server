package com.example.repo

import com.example.dp.table.CommentTable
import com.example.dp.table.LikeTable
import com.example.request.CommentRequest
import com.example.request.LikeRequest
import com.example.utils.TimeFormatter
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

class CommentRepositoryImpl(
    private val database: Database
) : CommentRepository {

    override fun insertComment(comment: CommentRequest, username: String) =
        transaction {
            CommentTable.insert {
                it[this.username] = username
                it[this.pid] = comment.pid
                it[this.commentContent] = comment.content
                it[this.time] = DateTime()
            }.get(CommentTable.pid)
        }

    override fun deleteComment(commentId: Int): Int =
        transaction {
            CommentTable
                .deleteWhere {
                    CommentTable.commentId eq commentId
                }
        }
}
