package com.alexproject.testapplication.viewModels

import androidx.lifecycle.ViewModel
import com.alexproject.domain.useCases.LoadStatisticsUseCase
import javax.inject.Inject

class FragmentStatisticsViewModel @Inject constructor(
    private val loadStatisticsUseCase: LoadStatisticsUseCase
) : ViewModel() {
    suspend fun loadStatistic(leagueId: Int) = loadStatisticsUseCase.loadStatistic(leagueId)
}