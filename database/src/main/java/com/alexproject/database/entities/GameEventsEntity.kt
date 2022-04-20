package com.alexproject.database.entities

import androidx.room.*
import com.alexproject.repository.models.GameEventsDTO

const val TABLE_GAME_EVENTS = "GameEvents"

@Entity(
    tableName = TABLE_GAME_EVENTS,
    foreignKeys = [
        ForeignKey(
            entity = TeamEntity::class,
            parentColumns = ["id"],
            childColumns = ["teamId"]
        )
    ]
)
data class GameEventsEntity(
    val assistsFirst: String?,
    val assistsSecond: String?,
    val comment: String?,
    @PrimaryKey
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
    fun mapper() = GameEventsDTO(
        gameEvents.assistsFirst,
        gameEvents.assistsSecond,
        gameEvents.comment,
        gameEvents.game_id,
        gameEvents.minute,
        gameEvents.period,
        gameEvents.players,
        team.toTeam(),
        gameEvents.type
    )
}