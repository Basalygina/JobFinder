package com.example.domain.favorites

import com.example.domain.models.Vacancy
import kotlinx.coroutines.flow.Flow

interface FavoritesInteractor {
    fun getFavorites(): Flow<List<Vacancy>>
    suspend fun toggleFavorite(vacancy: Vacancy)
    suspend fun getFavoriteVacancyIds() : List<String>
}