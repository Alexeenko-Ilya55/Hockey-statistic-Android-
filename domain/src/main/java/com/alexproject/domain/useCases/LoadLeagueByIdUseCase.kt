package com.alexproject.domain.useCases

import com.alexproject.domain.Repository
import javax.inject.Inject

class LoadLeagueByIdUseCase @Inject constructor(
    private val repository: Repository
){
    suspend fun loadLeagueById(leagueId: Int) = repository.loadLeagueById(leagueId)
}