package com.example.di

import org.jetbrains.exposed.sql.Database
import org.koin.dsl.module

val databaseModule = module {
    single<Database> {
        Database.connect(
            url = "jdbc:mysql://localhost:3306/social_media",
            password = "harera",
            user = "root",
            driver = "com.mysql.cj.jdbc.Driver"
        )
    }
}