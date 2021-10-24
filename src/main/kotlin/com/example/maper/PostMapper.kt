package com.example.maper

import com.example.dp.table.PostTable
import com.example.dp.table.UserTable
import com.example.model.User
import com.example.response.Post
import org.jetbrains.exposed.sql.ResultRow

object PostMapper {

    fun postFromResultRow(resultRow: ResultRow): Post =
        Post(
            pid = resultRow[PostTable.pid],
            time = resultRow[PostTable.time],
            caption = resultRow[PostTable.caption],
            imageUrl = resultRow[PostTable.imageUrl],
            user = User(
                name = resultRow[UserTable.name],
                password = resultRow[UserTable.password],
                bio = resultRow[UserTable.bio],
                username = resultRow[UserTable.userName],
                uid = resultRow[UserTable.uid],
                user_image_url = resultRow[UserTable.user_image_url],
                email = resultRow[UserTable.email],
                phone_number = resultRow[UserTable.phoneNumber],
            ),
        )
}