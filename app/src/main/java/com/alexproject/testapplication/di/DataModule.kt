package com.alexproject.testapplication.di

import android.content.Context
import com.alexproject.api.ApiRepositoryImpl
import com.alexproject.api.ApiService
import com.alexproject.database.AppDataBase
import com.alexproject.database.Dao
import com.alexproject.database.DatabaseImpl
import com.alexproject.domain.Repository
import com.alexproject.repository.ApiRepository
import com.alexproject.repository.Database
import com.alexproject.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideRepository(apiRepository: ApiRepository, database: Database): Repository =
        RepositoryImpl(apiRepository = apiRepository, database = database)

    @Provides
    @Singleton
    fun provideApiRepository(apiService: ApiService): ApiRepository =
        ApiRepositoryImpl(apiService = apiService)

    @Provides
    @Singleton
    fun provideDatabase(dao: Dao): Database =
        DatabaseImpl(dao)


    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): Dao =
        AppDataBase.buildsDatabase(context, DATABASE_NAME).Dao()

    @Provides
    @Singleton
    fun provideApiService(): ApiService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService::class.java)
}

private const val DATABASE_NAME = "AlexNews"
private const val BASE_URL = "https://v1.hockey.api-sports.io/"