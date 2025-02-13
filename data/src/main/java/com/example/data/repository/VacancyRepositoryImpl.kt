package com.example.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.example.data.mapper.toDomain
import com.example.data.remote.ApiService
import com.example.domain.api.VacancyRepository
import com.example.domain.models.Resource
import com.example.domain.models.ServerResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class VacancyRepositoryImpl(
    private val apiService: ApiService,
    private val context: Context
) : VacancyRepository {

    override fun getServerResponse(): Flow<Resource<ServerResponse>> = flow {
        if (!isConnected()) {
            emit(Resource.Error("No internet connection"))
            return@flow
        }

        val fullUrl = "https://drive.usercontent.google.com/u/0/uc?id=1z4TbeDkbfXkvgpoJprXbN85uCcD7f00r&export=download"
        val responseDto = apiService.downloadData(fullUrl)
        val response = responseDto.toDomain()
        emit(Resource.Success(response))
    }.catch { e ->
        emit(Resource.Error(e.message ?: "Unknown error"))
    }


    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities != null && (
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                )
    }

}