package com.example.data.repository

import com.example.data.local.dao.VacancyDao
import com.example.data.mappers.toDomain
import com.example.domain.favorites.FavoritesRepository
import com.example.domain.models.Vacancy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoritesRepositoryImpl(
    private val vacancyDao: VacancyDao
) : FavoritesRepository {
    override fun getFavoriteVacancies(): Flow<List<Vacancy>> =
        vacancyDao.getFavoriteVacancies().map { list ->
            list.map { it.toDomain() }
        }

    override suspend fun updateFavorite(vacancy: Vacancy, isFavorite: Boolean) {
        if (isFavorite) {
            vacancyDao.addFavoriteVacancy(vacancy)
        } else {
            vacancyDao.deleteFavoriteVacancyById(vacancy.id)
        }
    }

    override suspend fun getFavoriteVacancyIds(): List<String> =
        vacancyDao.getFavoriteVacancyIds()

}