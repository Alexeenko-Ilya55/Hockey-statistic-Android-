package com.alexproject.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

const val TABLE_FAVORITES_TEAM = "favoriteTeams"

@Entity(tableName = TABLE_FAVORITES_TEAM)
data class IsFavoriteTeamEntity(
    @PrimaryKey
    val teamId: Int
)