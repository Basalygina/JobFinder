package com.example.jobfinder.di

import com.example.domain.api.VacancyInteractor
import com.example.domain.favorites.FavoritesInteractor
import com.example.domain.impl.FavoritesInteractorImpl
import com.example.domain.impl.VacancyInteractorImpl
import org.koin.dsl.module

val interactorModule = module {

    single<VacancyInteractor> {
        VacancyInteractorImpl(get())
    }
    single<FavoritesInteractor> {
        FavoritesInteractorImpl(get())
    }

}
