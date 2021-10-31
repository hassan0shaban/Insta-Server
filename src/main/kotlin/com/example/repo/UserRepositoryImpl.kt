package com.example.repo

import com.example.dp.table.FollowRequestTable
import com.example.dp.table.FollowerTable
import com.example.dp.table.UserTable
import com.example.maper.Mapper
import com.example.maper.Mapper.userFromResultRow
import com.example.model.Follower
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

    override fun getConnections(username: String): List<Follower> =
        transaction {
            FollowerTable
                .select(
                    where = {
                        FollowerTable.followerUid eq username
                    }
                )
                .map {
                    Mapper.geFollowerFromResultRow(it)
                }
        }

    override fun addConnection(followerUid: String, username: String) =
        transaction {
            FollowerTable.insert {
                it[this.followerUid] = followerUid
                it[this.username] = username
                it[this.time] = DateTime()
            }.getOrNull(FollowerTable.username)
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

    override fun getFollowers(username: String): List<Follower> =
        transaction {
            FollowerTable
                .select(
                    where = { FollowerTable.username eq username }
                )
                .map {
                    Mapper.followerFromResultRow(it)
                }
        }

    override fun insertUser(email: String, password: String, name: String): Int? {
        return transaction {
            UserTable.insert {
                it[this.email] = email
                it[this.password] = password
                it[this.name] = name
            }.getOrNull(UserTable.uid)
                ?.also { uid ->
                    UserTable.update({ UserTable.uid eq uid }) {
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