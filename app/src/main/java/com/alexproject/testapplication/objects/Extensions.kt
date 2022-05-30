package com.alexproject.testapplication.objects

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.alexproject.domain.models.Game

fun List<Game>.filterGamesResults() = this.filter {
    it.status == Status.GAME_FINISHED.get ||
            it.status == Status.AFTER_OVER_TIME.get ||
            it.status == Status.AFTER_PENALTIES.get
}

fun List<Game>.filterGamesCalendar() = this.filter {
    it.status == Status.GAME_NOT_STARTED.get ||
            it.status == Status.POSTPONED.get ||
            it.status == Status.GAME_CANCELED.get
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)