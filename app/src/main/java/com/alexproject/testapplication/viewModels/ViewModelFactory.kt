package com.alexproject.testapplication.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alexproject.domain.useCases.*

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val addGameToFavoritesUseCase: AddGameToFavoritesUseCase,
    private val deleteGameFromFavoritesUseCase: DeleteGameFromFavoritesUseCase,
    private val loadGamesByDateUseCase: LoadGamesByDateUseCase,
    private val addTeamToFavoritesUseCase: AddTeamToFavoritesUseCase,
    private val deleteTeamFromFavoritesUseCase: DeleteTeamFromFavoritesUseCase,
    private val loadStatisticsUseCase: LoadStatisticsUseCase,
    private val loadFavoritesGamesUseCase: LoadFavoritesGamesUseCase,
    private val loadFavoritesTeamsUseCase: LoadFavoritesTeamsUseCase,
    private val loadH2HGamesUseCase: LoadH2HGamesUseCase,
    private val loadGameEventsUseCase: LoadGameEventsUseCase,
    private val loadLiveGamesUseCase: LoadLiveGamesUseCase,
    private val loadTeamGamesUseCase: LoadTeamGamesUseCase,
    private val loadGamesFromApiToDBUseCase: LoadGamesFromApiToDBUseCase,
    private val loadGameByIdUseCase: LoadGameByIdUseCase,
    private val loadTeamByIdUseCase: LoadTeamByIdUseCase,
    private val loadLeagueByIdUseCase: LoadLeagueByIdUseCase,
    private val loadCountryByIdUseCase: LoadCountryByIdUseCase,
    private val loadGamesForLeagueUseCase: LoadGamesForLeagueUseCase,
    private val loadAllLeaguesUseCase: LoadAllLeaguesUseCase,
    private val updateGamesByDateUseCase: UpdateGamesByDateUseCase,
    private val updateGameEventsUseCase: UpdateGameEventsUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            FragmentAllGamesViewModel::class.java -> FragmentAllGamesViewModel(
                loadGamesByDateUseCase, addGameToFavoritesUseCase, deleteGameFromFavoritesUseCase,
                updateGamesByDateUseCase
            )
            FragmentFavoritesViewModel::class.java -> FragmentFavoritesViewModel(
                addTeamToFavoritesUseCase,
                deleteTeamFromFavoritesUseCase,
                addGameToFavoritesUseCase,
                deleteGameFromFavoritesUseCase,
                loadFavoritesGamesUseCase,
                loadFavoritesTeamsUseCase
            )
            FragmentGameViewModel::class.java -> FragmentGameViewModel(
                loadH2HGamesUseCase,
                loadGameEventsUseCase,
                addTeamToFavoritesUseCase,
                deleteTeamFromFavoritesUseCase,
                loadGameByIdUseCase,
                addGameToFavoritesUseCase,
                deleteGameFromFavoritesUseCase,
                updateGameEventsUseCase
            )
            FragmentLiveViewModel::class.java -> FragmentLiveViewModel(
                addGameToFavoritesUseCase,
                deleteGameFromFavoritesUseCase,
                loadLiveGamesUseCase,
                updateGamesByDateUseCase
            )
            FragmentStatisticsViewModel::class.java -> FragmentStatisticsViewModel(
                loadStatisticsUseCase,
                loadLeagueByIdUseCase,
                addGameToFavoritesUseCase,
                deleteGameFromFavoritesUseCase,
                loadGamesForLeagueUseCase,
                loadAllLeaguesUseCase
            )
            FragmentTeamViewModel::class.java -> FragmentTeamViewModel(
                addTeamToFavoritesUseCase,
                deleteTeamFromFavoritesUseCase,
                loadTeamGamesUseCase,
                loadTeamByIdUseCase,
                loadStatisticsUseCase,
                addGameToFavoritesUseCase,
                deleteGameFromFavoritesUseCase
            )
            MainActivityViewModel::class.java -> MainActivityViewModel()
            else -> MainActivityViewModel()
        } as T
    }
}
