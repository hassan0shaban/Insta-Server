package com.example.di

import com.example.repo.PostRepository
import com.example.repo.PostRepositoryImpl
import com.example.repo.UserRepository
import com.example.repo.UserRepositoryImpl
import org.koin.dsl.module

val repoModule = module {
    single<UserRepository> {
        UserRepositoryImpl(get())
    }

    single<PostRepository> {
        PostRepositoryImpl(get())
    }

}