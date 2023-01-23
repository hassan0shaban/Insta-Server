package com.example.repo

import com.example.db.table.*
import com.example.maper.Mapper
import com.example.maper.Mapper.connectionFromResultRow
import com.example.maper.Mapper.userFromResultRow
import com.example.model.Connection
import com.example.model.FollowRequest
import com.example.model.User
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

class UserRepositoryImpl(
    private val database: Database
) : UserRepository {

    override fun checkUser(email: String): Int? =
        transaction {
            UserTable
                .select(where = { UserTable.email eq email })
                .firstOrNull()
                ?.let {
                    return@transaction userFromResultRow(it).uid
                }
        }

    override fun getUserByEmail(email: String): User? =
        transaction {
            UserTable
                .select(where = { UserTable.email eq email })
                .firstOrNull()
                ?.let {
                    return@transaction userFromResultRow(it)
                }
        }

    override fun getConnections(username: String): List<Connection> {
        val usernameList = transaction {
            ConnectionsTable
                .select {
                    (ConnectionsTable.user1 eq username)
                }.map {
                    it[ConnectionsTable.user2]
                }
        }

        return usernameList.map {
            transaction {
                UserTable
                    .select {
                        (UserTable.username eq it)
                    }.first().let {
                        connectionFromResultRow(it)
                    }
            }
        }
    }

    override fun getChatConnections(username: String): List<Connection> =
        transaction {
            ConnectionsTable
                .join(UserTable, JoinType.INNER, null) {
                    ((ConnectionsTable.user1 eq UserTable.username) or
                            (ConnectionsTable.user2 eq UserTable.username)) and
                            (UserTable.username neq username)
                }
                .select {
                    ((ConnectionsTable.user2 eq username) or
                            (ConnectionsTable.user1 eq username))
                }
                .groupBy(UserTable.username)
                .map {
                    Mapper.connectionFromResultRow(it)
                }
        }


    override fun insertConnection(followerUid: String, username: String) = kotlin.runCatching {
        transaction {
            ConnectionsTable
                .insert {
                    it[this.user1] = followerUid
                    it[this.user2] = username
                    it[this.time] = DateTime()
                }
                .execute(this)

            ConnectionsTable
                .insert {
                    it[this.user1] = username
                    it[this.user2] = followerUid
                    it[this.time] = DateTime()
                }
                .execute(this)
        }
    }

    override fun insertFollowRequest(followerUid: String, username: String) =
        transaction {
            FollowRequestTable.insert {
                it.set(this.followerUsername, followerUid)
                it.set(this.username, username)
                it.set(this.time, DateTime())
            }.getOrNull(FollowRequestTable.username)
        }

    override fun deleteFollowRequest(followerUid: String, username: String) =
        transaction {
            FollowRequestTable.deleteWhere {
                FollowRequestTable.followerUsername eq followerUid
                FollowRequestTable.username eq username
            }
        }

    override fun getFollowRequests(username: String): List<FollowRequest> =
        transaction {
            FollowRequestTable
                .select(
                    where = {
                        FollowRequestTable.username eq username
                    }
                ).map {
                    Mapper.followRequestFromResultRow(it)
                }
        }

    override fun getFollowers(username: String): List<Connection> =
        transaction {
            ConnectionsTable
                .select(
                    where = { ConnectionsTable.user2 eq username }
                )
                .map {
                    Mapper.connectionFromResultRow(it)
                }
        }

    override suspend fun deleteLike(pid: Int, username: String): Result<Int> = kotlin.runCatching {
        transaction {
            LikeTable
                .deleteWhere {
                    (LikeTable.pid eq pid) and (LikeTable.username eq username)
                }
        }
    }

    override fun updateName(username: String, name: String): Int =
        transaction {
            UserTable
                .update(
                    where = {
                        (UserTable.username eq username)
                    }
                ) {
                    it.set(UserTable.name, name)
                }
        }

    override fun insertUser(email: String, password: String, name: String): Result<Int> = kotlin.runCatching {
        transaction {
            UserTable.insert {
                it[this.email] = email
                it[this.password] = password
                it[this.name] = name
            }.get(UserTable.uid).also { uid ->
                UserTable.update(where = { UserTable.uid eq uid }) {
                    it[username] = uid.toString()
                }
            }
        }
    }

    override fun login(email: String, password: String) =
//        TODO use JWT
        transaction {
            UserTable.update(where = { UserTable.email eq email }) {
                it[token] = "//TODO TOKEN HERE"
            }

            return@transaction "//TODO TOKEN HERE"
        }

    override fun getUser(email: String, password: String): User? =
        transaction {
            val resultRow = UserTable
                .select(
                    where = {
                        (UserTable.email eq email) and (UserTable.password eq password)
                    }
                )
                .firstOrNull()
                ?: kotlin.run {
                    return@transaction null
                }

            return@transaction userFromResultRow(resultRow = resultRow)
        }

    override fun getUser(username: String): User? =
        transaction {
            val resultRow = UserTable
                .select(where = { UserTable.username eq username })
                .firstOrNull()
                ?: kotlin.run {
                    return@transaction null
                }

            return@transaction userFromResultRow(resultRow)
        }

    override fun updateUsername(username: String, newUsername: String): Int {
        return transaction {
            return@transaction UserTable
                .update(where = { UserTable.username eq username }) { it[this.username] = newUsername }
        }
    }
}