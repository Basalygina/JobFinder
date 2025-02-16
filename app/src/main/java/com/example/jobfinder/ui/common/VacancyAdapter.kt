package com.example.jobfinder.ui.common

import com.example.domain.models.Vacancy
import com.example.jobfinder.ui.delegates.vacancyAdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter


class VacancyAdapter(
    private val onFavoriteClick: (Vacancy) -> Unit,
    private val onItemClick: (Vacancy) -> Unit
) : AsyncListDifferDelegationAdapter<Vacancy>(VacancyDiffCallback()) {
    private var isFullContent: Boolean = false

    // Адаптер вакансий с поддержкой DiffUtil
    init {
        delegatesManager.addDelegate(vacancyAdapterDelegate(onFavoriteClick, onItemClick))
    }

    // Обновление списка с учетом режима отображения (полный/краткий)
    fun submitList(newVacancies: List<Vacancy>, showAll: Boolean) {
        isFullContent = showAll
        val displayedList = if (showAll) newVacancies else newVacancies.take(3)
        items = displayedList
    }
}