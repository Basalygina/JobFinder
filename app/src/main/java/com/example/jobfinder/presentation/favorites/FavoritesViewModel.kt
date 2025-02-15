package com.example.jobfinder.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.favorites.FavoritesInteractor
import com.example.domain.models.Vacancy
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val favoritesInteractor: FavoritesInteractor
) : ViewModel() {

    private val _state = MutableStateFlow<FavoritesScreenState>(FavoritesScreenState.Loading)
    val state: StateFlow<FavoritesScreenState> = _state

    private val _favoritesCount = MutableStateFlow(0)
    val favoritesCount: StateFlow<Int> = _favoritesCount

    init {
        viewModelScope.launch {
            favoritesInteractor.getFavorites().collect { favs ->
                _state.value = when {
                    favs.isEmpty() -> FavoritesScreenState.Empty
                    else -> FavoritesScreenState.Content(favs)
                }

                _favoritesCount.value = favs.size
            }
        }
    }

    fun toggleFavorite(vacancy: Vacancy) {
        viewModelScope.launch {
            favoritesInteractor.toggleFavorite(vacancy)
        }
    }
}