package com.robosoft.newspoc.di

import com.robosoft.newspoc.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        HomeViewModel(get () )
    }
}