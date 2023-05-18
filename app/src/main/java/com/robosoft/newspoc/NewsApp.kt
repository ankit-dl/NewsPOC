package com.robosoft.newspoc

import android.app.Application
import com.robosoft.newspoc.di.appModule
import com.robosoft.newspoc.di.repoModule
import com.robosoft.newspoc.di.viewModelModule
import org.koin.android.ext.koin.androidContext

import org.koin.core.context.startKoin


class NewsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NewsApp)
            modules(listOf(appModule, repoModule, viewModelModule))
        }
    }
}