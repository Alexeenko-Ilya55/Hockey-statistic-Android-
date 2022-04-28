package com.alexproject.domain.useCases

import com.alexproject.domain.Repository
import javax.inject.Inject

class LoadStatisticsUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun loadStatistic(leagueId: Int) = repository.loadStatistics(leagueId)
}
