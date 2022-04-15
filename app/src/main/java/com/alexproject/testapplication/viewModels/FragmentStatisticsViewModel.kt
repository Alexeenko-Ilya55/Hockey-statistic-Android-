package com.alexproject.testapplication.viewModels

import androidx.lifecycle.ViewModel
import com.alexproject.domain.useCases.StatisticsUseCase
import javax.inject.Inject

class FragmentStatisticsViewModel @Inject constructor(
    private val statisticsUseCase: StatisticsUseCase
) : ViewModel() {

    suspend fun loadStatistic() = statisticsUseCase.loadStatistic()
}