package com.alexproject.domain.useCases

import android.util.Log
import com.alexproject.domain.Repository
import com.alexproject.domain.models.EventsAdapterItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class LoadGameEventsUseCase @Inject constructor(
    private val repository: Repository
){
    suspend fun loadGameEvents(gameId: Int): Flow<List<EventsAdapterItem.GameEvents>> =
        repository.loadGameEvents(gameId)
}