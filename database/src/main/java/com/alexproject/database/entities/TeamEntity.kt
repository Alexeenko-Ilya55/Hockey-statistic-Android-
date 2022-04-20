package com.alexproject.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alexproject.repository.models.TeamDTO

const val TABLE_TEAM = "Team"

@Entity(tableName = TABLE_TEAM)
data class TeamEntity(
    @PrimaryKey
    val id: Int,
    val logo: String,
    val name: String,
    var isFavorite: Boolean
) {
    fun toTeam() = TeamDTO(id, logo, name, isFavorite)
}