package com.example.jobfinder.presentation.search

import com.example.domain.models.Offer
import com.example.domain.models.Vacancy

sealed class SearchScreenState {
    data object Loading : SearchScreenState()
    data class Error(val message: String) : SearchScreenState()
    data class Content(
        val vacancies: List<Vacancy>,
        val isFullContent: Boolean,
        val offers: List<Offer>?
    ) : SearchScreenState()
}