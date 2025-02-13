package com.example.jobfinder.di


import com.example.data.remote.ApiService
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val baseUrlMock =
    "https://drive.usercontent.google.com/"

val dataModule = module {

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(baseUrlMock)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<ApiService> {
        get<Retrofit>().create(ApiService::class.java)
    }

}
