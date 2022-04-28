package com.alexproject.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.alexproject.repository.models.TeamDTO

const val TABLE_TEAM = "Team"

@Entity(tableName = TABLE_TEAM)
data class TeamEntity(
    @PrimaryKey
    val id: Int,
    val logo: String,
    val name: String
) {
    fun mapToDTO() =
        TeamDTO(id, logo, name, false)
}

data class Team(
    @Embedded val teamEntity: TeamEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "teamId"
    )
    val isFavoriteTeamEntity: IsFavoriteTeamEntity?
) {
    fun mapToDTO() =
        TeamDTO(teamEntity.id, teamEntity.logo, teamEntity.name, isFavoriteTeamEntity != null)
}