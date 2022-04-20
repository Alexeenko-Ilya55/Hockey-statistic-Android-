package com.alexproject.domain.useCases

import com.alexproject.domain.Repository
import javax.inject.Inject

class LoadGamesByDateUseCase @Inject constructor(
    private val repository: Repository
){
    suspend fun loadGamesByDate(date: String) =
        repository.loadGamesByDate(date)
}