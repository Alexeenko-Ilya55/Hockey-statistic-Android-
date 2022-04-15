package com.alexproject.domain.useCases

import com.alexproject.domain.Repository
import javax.inject.Inject

class SearchInfoUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun searchGameByTeamName(teamName: String) = repository.searchGameByTeamName(teamName)
    suspend fun searchGameByLeagueName(leagueName: String) =
        repository.searchGameByLeagueName(leagueName)

    suspend fun searchGameByCountryIndex(countryIndex: String) =
        repository.searchGameByCountryIndex(countryIndex)

    suspend fun searchTeam(teamName: String) = repository.searchTeam(teamName)
}