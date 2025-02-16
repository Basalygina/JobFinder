package com.example.jobfinder.di


import androidx.room.Room
import com.example.data.local.AppDatabase
import com.example.data.local.dao.VacancyDao
import com.example.data.remote.ApiService
import org.koin.android.ext.koin.androidContext
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

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "app_database.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    single<VacancyDao> {
        get<AppDatabase>().vacancyDao()
    }

}
