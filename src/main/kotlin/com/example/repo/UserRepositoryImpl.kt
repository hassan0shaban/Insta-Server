package com.example.repo

import com.example.dp.table.ConnectionTable
import com.example.dp.table.UserTable
import com.example.maper.Mapper
import com.example.maper.Mapper.userFromResultRow
import com.example.model.Connection
import com.example.model.User
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepositoryImpl(
    private val database: Database
) : UserRepository {

    override fun checkUser(email: String): Int? {
        return transaction {
            UserTable
                .select(where = { UserTable.email eq email })
                .firstOrNull()
                ?.let {
                    return@transaction userFromResultRow(it).uid
                }
        }
    }

    override fun getUserByEmail(email: String): User? {
        return transaction {
            UserTable
                .select(where = { UserTable.email eq email })
                .firstOrNull()
                .let {
                    it?.let {
                        return@transaction userFromResultRow(it)
                    }
                    return@transaction null
                }
        }
    }

    override fun getConnections(username: String): List<Connection> =
        transaction {
            ConnectionTable
                .select(
                    where = {
                        ConnectionTable.followerUid eq username
                    }
                )
                .map {
                    Mapper.getConnectionFromResultRow(it)
                }
        }


    override fun insertUser(email: String, password: String): Int? {
        return transaction {
            UserTable.insert {
                it[this.email] = email
                it[this.password] = password
            }.getOrNull(UserTable.uid)
                ?.also { uid ->
                    UserTable.update({ UserTable.uid eq uid }) {
                        it[userName] = uid.toString()
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
                .select(where = { UserTable.userName eq username })
                .firstOrNull()
                ?: kotlin.run {
                    return@transaction null
                }

            return@transaction userFromResultRow(resultRow)
        }

    override fun updateUsername(username: String, newUsername: String): Int {
        return transaction {
            return@transaction UserTable
                .update(where = { UserTable.userName eq username }) { it[userName] = newUsername }
        }
    }
}