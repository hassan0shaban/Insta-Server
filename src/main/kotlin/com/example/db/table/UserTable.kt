package com.example.db.table

import com.example.db.utils.TableNames
import com.example.db.utils.UserFields
import org.jetbrains.exposed.sql.Table

object UserTable : Table(TableNames.User) {
    val uid = integer(UserFields.uid).primaryKey().autoIncrement()
    val email = varchar(UserFields.email, 120).primaryKey()
    val username = varchar(UserFields.username, 80).primaryKey()
    val password = varchar(UserFields.password, 50)
    val phoneNumber = varchar(UserFields.phone_number, 45).nullable()
    val userImageUrl = varchar(UserFields.user_image_url, 300).nullable()
    val name = varchar(UserFields.name, 45)
    val token = varchar(UserFields.token, 3000).nullable()
    val bio = varchar(UserFields.bio, 300).nullable()
}
