package com.example.jobfinder.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.api.VacancyInteractor
import com.example.domain.favorites.FavoritesInteractor
import com.example.domain.models.Vacancy
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SearchViewModel(
    private val vacancyInteractor: VacancyInteractor,
    private val favoritesInteractor: FavoritesInteractor
) : ViewModel() {
    private val _searchScreenState = MutableStateFlow<SearchScreenState>(SearchScreenState.Loading)
    val searchScreenState: StateFlow<SearchScreenState> = _searchScreenState

    init {
        loadData()
    }

    private fun loadData() {
        _searchScreenState.value = SearchScreenState.Loading
        viewModelScope.launch {
            runCatching {
                vacancyInteractor.getServerResponse().first()
            }.fold(
                onSuccess = { serverResponse ->
                    val vacanciesFromServer = serverResponse.first?.vacancies ?: emptyList()
                    val offers = serverResponse.first?.offers
                    val errorMessage = serverResponse.second

                    val favoriteIds = favoritesInteractor.getFavoriteVacancyIds().toSet()
                    val updatedVacancies =
                        updateVacanciesFavoriteFlag(vacanciesFromServer, favoriteIds)

                    if (vacanciesFromServer.isEmpty()) {
                        _searchScreenState.value = SearchScreenState.Error(errorMessage.toString())
                    } else {
                        _searchScreenState.value = SearchScreenState.Content(
                            vacancies = updatedVacancies,
                            isFullContent = false,
                            offers = offers
                        )
                    }
                },
                onFailure = { e ->
                    _searchScreenState.value = SearchScreenState.Error(e.message ?: "Unknown error")
                }
            )
        }
    }

    private fun updateVacanciesFavoriteFlag(
        vacancies: List<Vacancy>,
        favoriteIds: Set<String>
    ): List<Vacancy> {
        return vacancies.map { it.copy(isFavorite = favoriteIds.contains(it.id)) }
    }

    fun toggleVacanciesViewMode() {
        val currentState = _searchScreenState.value
        if (currentState is SearchScreenState.Content) {
            _searchScreenState.value =
                currentState.copy(isFullContent = !currentState.isFullContent)
        }
    }

    fun toggleFavorite(vacancy: Vacancy) {
        viewModelScope.launch {
            val toggledVacancy = vacancy.copy(isFavorite = !vacancy.isFavorite)
            favoritesInteractor.toggleFavorite(vacancy)
            val currentState = _searchScreenState.value
            if (currentState is SearchScreenState.Content) {
                _searchScreenState.value = currentState.copy(
                    vacancies = currentState.vacancies.map {
                        if (it.id == toggledVacancy.id) toggledVacancy else it
                    }
                )
            }
        }
    }

    fun refreshFavorites() {
        viewModelScope.launch {
            val favoriteIds = favoritesInteractor.getFavoriteVacancyIds().toSet()
            val currentState = _searchScreenState.value
            if (currentState is SearchScreenState.Content) {
                _searchScreenState.value = currentState.copy(
                    vacancies = currentState.vacancies.map { vacancy ->
                        vacancy.copy(isFavorite = favoriteIds.contains(vacancy.id))
                    }
                )
            }
        }
    }

}