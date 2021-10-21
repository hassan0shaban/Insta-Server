package com.example.repo

import com.example.model.User
import com.example.dp.table.UserTable
import com.example.maper.Mapper.userFromResultRow
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepositoryImpl(
    private val database: Database
) : UserRepository {

    override fun insertUser(user: User): Int? {
        return transaction {
            return@transaction UserTable
                .insert {
                    it[name] = user.name
                    it[bio] = user.bio
                    it[email] = user.email
                    it[phoneNumber] = user.phone_number
                }
                .getOrNull(UserTable.uid)
                ?.also { uid ->
                    UserTable.update({ UserTable.uid eq uid }) {
                        it[userName] = uid.toString()
                    }
                }
        }
    }

    override fun getUser(username: String): User? {
        return transaction {
            return@transaction UserTable
                .select(where = { UserTable.userName eq username })
                .firstOrNull()
                .let {
                    userFromResultRow(it)
                }
        }
    }

    override fun updateUsername(username: String, newUsername: String): Int {
        return transaction {
            return@transaction UserTable
                .update(where = { UserTable.userName eq username }) { it[userName] = newUsername }
        }
    }
}