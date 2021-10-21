package com.example.dp.table

import com.harera.insta.data.dp.utils.TableNames
import com.example.dp.utils.UserFields
import org.jetbrains.exposed.sql.Table

object UserTable : Table(TableNames.User) {
    val uid = integer(UserFields.uid).primaryKey().autoIncrement()
    val userName = varchar(UserFields.username, 80).primaryKey().default(uid.toString())
    val phoneNumber = varchar(UserFields.phone_number, 45).nullable()
    val user_image_url = varchar(UserFields.user_image_url, 300).nullable()
    val name = varchar(UserFields.name, 45)
    val email = varchar(UserFields.email, 120).nullable()
    val bio = varchar(UserFields.bio, 300).nullable()
}