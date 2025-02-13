package com.example.jobfinder.di

import com.example.data.repository.VacancyRepositoryImpl
import com.example.domain.api.VacancyRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    single<VacancyRepository> {
        VacancyRepositoryImpl(get(), androidContext())
    }
}