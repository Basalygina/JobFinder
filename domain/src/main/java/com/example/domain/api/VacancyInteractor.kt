package com.example.domain.api

import com.example.domain.models.ServerResponse
import kotlinx.coroutines.flow.Flow

interface VacancyInteractor {
    fun getServerResponse(): Flow<Pair<ServerResponse?, String?>>
}