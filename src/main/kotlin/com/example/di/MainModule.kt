package com.example.di

import com.example.repo.*
import com.example.service.*
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

    single<UserRepository> { UserRepositoryImpl(get()) }

    single<PostRepository> { PostRepositoryImpl(get()) }

    single<MessageRepository> { MessageRepositoryImpl(get()) }

    single<LikeRepository> { LikeRepositoryImpl() }

    single<CommentRepository> { CommentRepositoryImpl(get()) }

    single { UserService(get(), get()) }

    single { PostService(get(), get()) }

    single { LikeService(get(), get()) }

    single { CommentService(get()) }

    single { MessageService(get()) }
}