package com.example.jobfinder

import android.app.Application
import com.example.jobfinder.di.dataModule
import com.example.jobfinder.di.interactorModule
import com.example.jobfinder.di.repositoryModule
import com.example.jobfinder.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(dataModule, interactorModule, repositoryModule, viewModelModule)
        }
    }
}