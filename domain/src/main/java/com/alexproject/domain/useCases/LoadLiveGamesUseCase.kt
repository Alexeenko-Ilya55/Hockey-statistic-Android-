package com.alexproject.domain.useCases

import com.alexproject.domain.Repository
import javax.inject.Inject

class LoadLiveGamesUseCase @Inject constructor(
    private val repository: Repository
){
    suspend fun loadLiveGames(date: String) =
        repository.loadLiveGames(date)
}