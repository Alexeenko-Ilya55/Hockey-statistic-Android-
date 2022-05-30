package com.alexproject.testapplication.di

import com.alexproject.testapplication.activity.MainActivity
import com.alexproject.testapplication.app.AppDependencies
import com.alexproject.testapplication.fragments.*
import com.alexproject.testapplication.workManager.LeagueDownloadWorker
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [AppModule::class, DomainModule::class, DataModule::class],
    dependencies = [AppDependencies::class]
)
@Singleton
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(fragmentAllGames: FragmentAllGames)
    fun inject(fragmentFavorites: FragmentFavorites)
    fun inject(fragmentGame: FragmentGame)
    fun inject(fragmentLive: FragmentLive)
    fun inject(fragmentStatistics: FragmentStatistics)
    fun inject(fragmentTeam: FragmentTeam)
    fun inject(leagueDownloadWorker: LeagueDownloadWorker)

    @Component.Builder
    interface Builder {
        fun appDependencies(appDependencies: AppDependencies): Builder
        fun build(): AppComponent
    }
}