package com.alexproject.testapplication.di

import androidx.navigation.findNavController
import com.alexproject.domain.useCases.FavoritesUseCase
import com.alexproject.domain.useCases.GameUseCase
import com.alexproject.domain.useCases.LoadGamesUseCase
import com.alexproject.domain.useCases.StatisticsUseCase
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
        favoritesUseCase: FavoritesUseCase,
        gameUseCase: GameUseCase,
        loadGamesUseCase: LoadGamesUseCase,
        statisticsUseCase: StatisticsUseCase
    ) = ViewModelFactory(favoritesUseCase, gameUseCase, loadGamesUseCase, statisticsUseCase)
}