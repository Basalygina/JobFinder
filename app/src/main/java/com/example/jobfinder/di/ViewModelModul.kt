package com.example.jobfinder.di

import com.example.jobfinder.presentation.favorites.FavoritesViewModel
import com.example.jobfinder.presentation.search.SearchViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        SearchViewModel(get(),get())
    }
    viewModel {
        FavoritesViewModel(get())
    }
}