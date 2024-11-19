package com.kirill.notifyapplication.di

import com.kirill.notifyapplication.presentation.viewmodel.NotifyViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel<NotifyViewModel> {
        NotifyViewModel(repository = get(), context = get())
    }
}