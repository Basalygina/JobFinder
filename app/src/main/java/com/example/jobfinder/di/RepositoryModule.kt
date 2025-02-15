package com.example.jobfinder.di

import com.example.data.repository.FavoritesRepositoryImpl
import com.example.data.repository.VacancyRepositoryImpl
import com.example.domain.api.VacancyRepository
import com.example.domain.favorites.FavoritesRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    single<VacancyRepository> {
        VacancyRepositoryImpl(get(), androidContext())
    }
    single<FavoritesRepository> {
        FavoritesRepositoryImpl(get())
    }
}