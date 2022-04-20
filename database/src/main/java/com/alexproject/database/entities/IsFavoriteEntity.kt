package com.alexproject.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

const val TABLE_FAVORITES_GAMES = "favoritesGames"

@Entity(tableName = TABLE_FAVORITES_GAMES)
data class IsFavoriteGameEntity(
    @PrimaryKey
    val gameId: Int,
    val favorite: Boolean
)