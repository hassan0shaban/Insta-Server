package com.example.maper

import com.example.dp.table.UserTable
import com.example.model.User
import org.jetbrains.exposed.sql.ResultRow

object Mapper {
    fun userFromResultRow(resultRow: ResultRow?): User? {
        if (resultRow == null)
            return null

        return User(
            name = resultRow[UserTable.name],
            bio = resultRow[UserTable.bio],
            username = resultRow[UserTable.userName],
            uid = resultRow[UserTable.uid],
            user_image_url = resultRow[UserTable.user_image_url],
            email = resultRow[UserTable.email],
            phone_number = resultRow[UserTable.phoneNumber],
        )
    }
}