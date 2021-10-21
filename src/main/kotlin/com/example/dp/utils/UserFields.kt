package com.example.dp.utils

import com.example.model.User
import com.example.dp.table.UserTable
import org.jetbrains.exposed.sql.ResultRow

object UserFields {
    const val uid = "uid"
    const val name = "name"
    const val username = "username"
    const val user_image_url = "user_image_url"
    const val bio = "bio"
    const val email = "email"
    const val phone_number = "phone_number"
}
