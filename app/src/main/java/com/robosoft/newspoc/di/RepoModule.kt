package com.robosoft.newspoc.di

import com.robosoft.newspoc.model.NewsRepository
import org.koin.dsl.module

val repoModule = module {
    single {
        NewsRepository(get())
    }
}