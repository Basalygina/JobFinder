package com.example.jobfinder.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.api.VacancyInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SearchViewModel(val vacancyInteractor: VacancyInteractor) : ViewModel() {
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
                    val vacancies = serverResponse.first?.vacancies
                    val offers = serverResponse.first?.offers
                    val errorMessage = serverResponse.second

                    _searchScreenState.value = when {
                        vacancies == null -> SearchScreenState.Error(errorMessage.toString())
                        else -> SearchScreenState.Content(
                            vacancies = vacancies,
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

    fun toggleVacanciesViewMode() {
        val currentState = _searchScreenState.value
        if (currentState is SearchScreenState.Content) {
            _searchScreenState.value = currentState.copy(isFullContent = !currentState.isFullContent)
        }
    }
}