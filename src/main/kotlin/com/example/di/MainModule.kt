package com.example.di

import com.example.repo.PostRepository
import com.example.repo.PostRepositoryImpl
import com.example.repo.UserRepository
import com.example.repo.UserRepositoryImpl
import com.example.service.PostService
import com.example.service.UserService
import org.jetbrains.exposed.sql.Database
import org.koin.dsl.module

val mainModule = module {
    single {
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

    single<PostRepository> {
        PostRepositoryImpl(get())
    }

    single { UserService(get(), get()) }

    single { PostService(get(), get()) }
}