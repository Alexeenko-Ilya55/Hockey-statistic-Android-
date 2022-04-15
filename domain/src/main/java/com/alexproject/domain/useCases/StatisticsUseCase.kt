package com.alexproject.domain.useCases

import com.alexproject.domain.Repository
import javax.inject.Inject

class StatisticsUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun loadStatistic() =
        repository.loadStatistics()
}