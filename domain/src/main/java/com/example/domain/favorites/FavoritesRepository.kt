package com.example.domain.favorites

import com.example.domain.models.Vacancy
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    fun getFavoriteVacancies(): Flow<List<Vacancy>>
    suspend fun updateFavorite(vacancy: Vacancy, isFavorite: Boolean)
    suspend fun getFavoriteVacancyIds(): List<String>
}