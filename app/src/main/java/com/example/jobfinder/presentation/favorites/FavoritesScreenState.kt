package com.example.jobfinder.presentation.favorites

import com.example.domain.models.Vacancy

sealed class FavoritesScreenState {
    data object Loading : FavoritesScreenState()
    data object Error: FavoritesScreenState()
    data object Empty: FavoritesScreenState()
    data class Content(val vacancies: List<Vacancy>) : FavoritesScreenState()
}