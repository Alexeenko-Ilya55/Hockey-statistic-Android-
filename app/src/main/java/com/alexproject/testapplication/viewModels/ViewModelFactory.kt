package com.alexproject.testapplication.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alexproject.domain.useCases.FavoritesUseCase
import com.alexproject.domain.useCases.GameUseCase
import com.alexproject.domain.useCases.LoadGamesUseCase
import com.alexproject.domain.useCases.StatisticsUseCase


@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val favoritesUseCase: FavoritesUseCase,
    private val gameUseCase: GameUseCase,
    private val loadGamesUseCase: LoadGamesUseCase,
    private val statisticsUseCase: StatisticsUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            FragmentAllGamesViewModel::class.java -> FragmentAllGamesViewModel(
                favoritesUseCase = favoritesUseCase,
                loadGamesUseCase = loadGamesUseCase
            )
            FragmentFavoritesViewModel::class.java -> FragmentFavoritesViewModel(
                useCaseFavorites = favoritesUseCase
            )
            FragmentGameViewModel::class.java -> FragmentGameViewModel(
                gameUseCase = gameUseCase,
                favoritesUseCase = favoritesUseCase
            )
            FragmentLiveViewModel::class.java -> FragmentLiveViewModel(
                favoritesUseCase = favoritesUseCase,
                loadGamesUseCase = loadGamesUseCase
            )
            FragmentStatisticsViewModel::class.java -> FragmentStatisticsViewModel(
                statisticsUseCase = statisticsUseCase
            )
            FragmentTeamViewModel::class.java -> FragmentTeamViewModel(
                favoritesUseCase = favoritesUseCase,
                loadGamesUseCase = loadGamesUseCase
            )
            MainActivityViewModel::class.java -> MainActivityViewModel()
            else -> MainActivityViewModel()
        } as T
    }
}
