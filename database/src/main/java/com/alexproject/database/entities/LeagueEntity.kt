package com.alexproject.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alexproject.repository.models.LeagueDTO

const val TABLE_LEAGUE = "League"

@Entity(tableName = TABLE_LEAGUE)
data class LeagueEntity(
    @PrimaryKey
    val id: Int,
    val logo: String,
    val name: String,
    val type: String
) {
    fun mapper() = LeagueDTO(id, logo, name, type)
}
