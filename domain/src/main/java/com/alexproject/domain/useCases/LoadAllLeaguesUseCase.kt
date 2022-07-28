package com.alexproject.domain.useCases

import com.alexproject.domain.Repository
import javax.inject.Inject

class LoadAllLeaguesUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun loadAllLeagues() = repository.loadAllLeagues()
}