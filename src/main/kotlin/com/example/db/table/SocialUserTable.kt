package com.example.db.table

import com.example.db.utils.TableNames
import com.example.db.utils.UserFields
import org.jetbrains.exposed.sql.Table

object SocialUserTable : Table(TableNames.User) {
    val uid = integer(UserFields.uid).primaryKey().autoIncrement()
    val username = varchar(UserFields.username, 80).primaryKey()
    val token = varchar(UserFields.token, 50)
    val email = varchar(UserFields.email, 120).nullable()
    val phoneNumber = varchar(UserFields.phone_number, 45).nullable()
    val user_image_url = varchar(UserFields.user_image_url, 300).nullable()
    val name = varchar(UserFields.name, 45).nullable()
    val bio = varchar(UserFields.bio, 300).nullable()
}
