package com.alexproject.domain.useCases

import com.alexproject.domain.Repository
import javax.inject.Inject

class UpdateAllLeaguesUseCase @Inject constructor(
    private val repository: Repository
){
    suspend fun updateAllLeagues() = repository.updateAllLeagues()
}