package com.alexproject.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.alexproject.repository.models.GameEventsDTO

const val TABLE_GAME_EVENTS = "GameEvents"

@Entity(tableName = TABLE_GAME_EVENTS)
data class GameEventsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val assistsFirst: String?,
    val assistsSecond: String?,
    val comment: String?,
    val game_id: Int,
    val minute: String,
    val period: String,
    val players: String,
    val teamId: Int,
    val type: String
)

data class GameEvents(
    @Embedded val gameEvents: GameEventsEntity,
    @Relation(
        parentColumn = "teamId",
        entityColumn = "id"
    )
    val team: TeamEntity,
) {
    fun mapToDTO() = GameEventsDTO(
        gameEvents.assistsFirst,
        gameEvents.assistsSecond,
        gameEvents.comment,
        gameEvents.game_id,
        gameEvents.minute,
        gameEvents.period,
        gameEvents.players,
        team.mapToDTO(),
        gameEvents.type
    )
}