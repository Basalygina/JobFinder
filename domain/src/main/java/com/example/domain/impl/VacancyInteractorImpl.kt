package com.example.domain.impl

import com.example.domain.api.VacancyInteractor
import com.example.domain.api.VacancyRepository
import com.example.domain.models.Resource
import com.example.domain.models.ServerResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class VacancyInteractorImpl(private val repository: VacancyRepository) : VacancyInteractor {

    override fun getServerResponse(): Flow<Pair<ServerResponse?, String?>> {
        return repository.getServerResponse().map { result ->
            when (result) {
                is Resource.Success -> Pair(result.data, null)
                is Resource.Error -> Pair(null, result.message)
            }
        }
    }
}