package com.example.domain.api

import com.example.domain.models.Resource
import com.example.domain.models.ServerResponse
import kotlinx.coroutines.flow.Flow

interface VacancyRepository {
    fun getServerResponse(): Flow<Resource<ServerResponse>>
}