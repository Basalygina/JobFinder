package com.example.domain.impl

import com.example.domain.favorites.FavoritesInteractor
import com.example.domain.favorites.FavoritesRepository
import com.example.domain.models.Vacancy
import kotlinx.coroutines.flow.Flow

class FavoritesInteractorImpl(
    private val favoritesRepository: FavoritesRepository
) : FavoritesInteractor {
    override fun getFavorites(): Flow<List<Vacancy>> = favoritesRepository.getFavoriteVacancies()

    override suspend fun toggleFavorite(vacancy: Vacancy) {
        val toggledVacancy = vacancy.copy(isFavorite = !vacancy.isFavorite)
        favoritesRepository.updateFavorite(toggledVacancy, toggledVacancy.isFavorite)
    }

    override suspend fun getFavoriteVacancyIds(): List<String> =
        favoritesRepository.getFavoriteVacancyIds()
}