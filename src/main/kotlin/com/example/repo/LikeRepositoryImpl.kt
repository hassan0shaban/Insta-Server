package com.example.repo

import com.example.dp.table.LikeTable
import com.example.request.LikeRequest
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

class LikeRepositoryImpl : LikeRepository {

    override fun insertLike(like: LikeRequest, username: String): Int =
        transaction {
            LikeTable.insert {
                it[this.username] = username
                it[this.pid] = like.pid
                it[this.time] = DateTime.now()
            }.get(LikeTable.pid)
        }

    override fun deleteLike(pid: Int, username: String) =
        transaction {
            LikeTable
                .deleteWhere {
                    (LikeTable.pid eq pid) and (LikeTable.username eq username)
                }
        }

    override fun getLike(username: String, pid: Int) =
        transaction {
            LikeTable.select {
                (LikeTable.pid eq pid) and (LikeTable.username eq username)
            }.firstOrNull()
        }
}