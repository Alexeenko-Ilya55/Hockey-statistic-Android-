package com.alexproject.testapplication.di

import androidx.navigation.findNavController
import com.alexproject.domain.useCases.*
import com.alexproject.testapplication.R
import com.alexproject.testapplication.activity.MainActivity
import com.alexproject.testapplication.viewModels.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideNavController(activity: MainActivity) =
        activity.findNavController(R.id.activity_nav_container)

    @Provides
    fun provideViewModelFactory(
        addGameToFavoritesUseCase: AddGameToFavoritesUseCase,
        deleteGameFromFavoritesUseCase: DeleteGameFromFavoritesUseCase,
        loadGamesByDateUseCase: LoadGamesByDateUseCase,
        addTeamToFavoritesUseCase: AddTeamToFavoritesUseCase,
        deleteTeamFromFavoritesUseCase: DeleteTeamFromFavoritesUseCase,
        loadStatisticsUseCase: LoadStatisticsUseCase,
        loadFavoritesGamesUseCase: LoadFavoritesGamesUseCase,
        loadFavoritesTeamsUseCase: LoadFavoritesTeamsUseCase,
        loadH2HGamesUseCase: LoadH2HGamesUseCase,
        loadGameEventsUseCase: LoadGameEventsUseCase,
        loadLiveGamesUseCase: LoadLiveGamesUseCase,
        loadTeamGamesUseCase: LoadTeamGamesUseCase,
        loadGamesFromApiToDBUseCase: LoadGamesFromApiToDBUseCase,
        loadGameByIdUseCase: LoadGameByIdUseCase,
        loadTeamByIdUseCase: LoadTeamByIdUseCase
    ) = ViewModelFactory(
        addGameToFavoritesUseCase,
        deleteGameFromFavoritesUseCase,
        loadGamesByDateUseCase,
        addTeamToFavoritesUseCase,
        deleteTeamFromFavoritesUseCase,
        loadStatisticsUseCase,
        loadFavoritesGamesUseCase,
        loadFavoritesTeamsUseCase,
        loadH2HGamesUseCase,
        loadGameEventsUseCase,
        loadLiveGamesUseCase,
        loadTeamGamesUseCase,
        loadGamesFromApiToDBUseCase,
        loadGameByIdUseCase,
        loadTeamByIdUseCase
    )
}