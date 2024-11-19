package com.kirill.notifyapplication.di

import com.kirill.notifyapplication.data.localdatasource.NotifyDatabase
import com.kirill.notifyapplication.data.repository.NotifyRepositoryImpl
import com.kirill.notifyapplication.domain.NotifyRepository
import org.koin.dsl.module

val dataModule = module {
    single<NotifyDatabase> {
        NotifyDatabase.getInstance(context = get())
    }

    single<NotifyRepository> {
        NotifyRepositoryImpl()
    }
}