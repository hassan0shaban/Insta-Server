package com.example.di

import com.example.service.UserService
import org.koin.dsl.module

val serviceModule = module {

    single { UserService(get(), get()) }
}