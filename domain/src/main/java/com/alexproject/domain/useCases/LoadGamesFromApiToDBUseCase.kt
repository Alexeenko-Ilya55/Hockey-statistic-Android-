package com.alexproject.domain.useCases

import com.alexproject.domain.Repository
import javax.inject.Inject

class LoadGamesFromApiToDBUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun loadGamesFromApiToDB(date: String) = repository.loadGamesFromApiToDB(date)
}