package com.alexproject.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.alexproject.repository.models.GameDTO

const val TABLE_GAMES = "Game"

@Entity(tableName = TABLE_GAMES)
data class GamesEntity(
    @PrimaryKey
    val id: Int,
    val countryId: Int,
    val date: String,
    val time: String,
    val events: Boolean,
    val leagueId: Int,
    val awayScores: Int?,
    val homeScores: Int?,
    val status: String,
    val awayTeamId: Int,
    val homeTeamId: Int,
    val timer: String?,
    val firstPeriod: String?,
    val overtime: String?,
    val penalties: String?,
    val secondPeriod: String?,
    val thirdPeriod: String?
)

data class Game(
    @Embedded val gamesEntity: GamesEntity,
    @Relation(
        parentColumn = "countryId",
        entityColumn = "id",
    )
    val country: CountryEntity,
    @Relation(
        parentColumn = "leagueId",
        entityColumn = "id"
    )
    val league: LeagueEntity,
    @Relation(
        parentColumn = "awayTeamId",
        entityColumn = "id"
    )
    val awayTeam: TeamEntity,
    @Relation(
        parentColumn = "homeTeamId",
        entityColumn = "id"
    )
    val homeTeam: TeamEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "gameId"
    )
    val favoritesGame: IsFavoriteGameEntity?
) {
    fun mapper() = GameDTO(
        gamesEntity.id,
        country.toCountry(),
        gamesEntity.date,
        gamesEntity.time,
        gamesEntity.events,
        league.mapper(),
        gamesEntity.awayScores,
        gamesEntity.homeScores,
        gamesEntity.status,
        awayTeam.toTeam(),
        homeTeam.toTeam(),
        gamesEntity.timer,
        gamesEntity.firstPeriod,
        gamesEntity.overtime,
        gamesEntity.penalties,
        gamesEntity.secondPeriod,
        gamesEntity.thirdPeriod,
        favoritesGame?.favorite ?: false
    )
}
