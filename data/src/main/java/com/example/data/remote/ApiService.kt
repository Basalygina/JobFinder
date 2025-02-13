package com.example.data.remote

import com.example.data.dto.ServerResponseDto
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET
    suspend fun downloadData(@Url url: String): ServerResponseDto
}