package com.example.di

import com.example.repo.UserRepository
import com.example.repo.UserRepositoryImpl
import com.example.service.UserService
import org.jetbrains.exposed.sql.Database
import org.koin.dsl.module

val mainModule = module {
    single<Database> {
        Database.connect(
            url = "jdbc:mysql://localhost:3306/social_media",
            password = "harera",
            user = "root",
            driver = "com.mysql.cj.jdbc.Driver"
        )
    }

    single<UserRepository> {
        UserRepositoryImpl(get())
    }

    single { UserService(get()) }
}