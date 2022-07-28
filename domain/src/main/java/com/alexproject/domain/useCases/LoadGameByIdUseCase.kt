package com.alexproject.domain.useCases

import com.alexproject.domain.Repository
import javax.inject.Inject

class LoadGameByIdUseCase @Inject constructor(
    private val repository: Repository
){
    suspend fun loadGameById(gameId: Int) = repository.loadGameById(gameId)
}