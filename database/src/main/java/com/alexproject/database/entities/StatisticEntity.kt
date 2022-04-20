package com.alexproject.database.entities

import androidx.room.Entity
import androidx.room.Relation
import com.alexproject.repository.models.StatisticDTO

const val TABLE_STATISTIC = "Statistic"

@Entity(
    tableName = TABLE_STATISTIC,
    primaryKeys = ["nameGroup"],
    foreignKeys = [
        androidx.room.ForeignKey(
            entity = CountryEntity::class,
            parentColumns = ["id"],
            childColumns = ["countryId"],
            onDelete = androidx.room.ForeignKey.NO_ACTION,
            onUpdate = androidx.room.ForeignKey.CASCADE
        ),
        androidx.room.ForeignKey(
            entity = LeagueEntity::class,
            parentColumns = ["id"],
            childColumns = ["leagueId"],
            onDelete = androidx.room.ForeignKey.NO_ACTION,
            onUpdate = androidx.room.ForeignKey.CASCADE
        ),
        androidx.room.ForeignKey(
            entity = TeamEntity::class,
            parentColumns = ["id"],
            childColumns = ["teamId"],
            onDelete = androidx.room.ForeignKey.NO_ACTION,
            onUpdate = androidx.room.ForeignKey.CASCADE
        ),
    ]
)
data class StatisticEntity(
    val countryId: Int,
    val description: String?,
    val form: String,
    val nameGroup: String,
    val leagueId: Int,
    val points: Int,
    val position: Int,
    val stage: String,
    val teamId: Int,
    val winOvertime: Int,
    val win: Int,
    val loseOvertime: Int,
    val lose: Int,
    val againstGoals: Int,
    val forGoals: Int
)

data class Statistic(
    @Relation(
        parentColumn = "countryId",
        entityColumn = "id"
    )
    val country: CountryEntity,
    val description: String?,
    val form: String,
    val nameGroup: String,
    @Relation(
        parentColumn = "leagueId",
        entityColumn = "id"
    )
    val league: LeagueEntity,
    val points: Int,
    val position: Int,
    val stage: String,
    @Relation(
        parentColumn = "teamId",
        entityColumn = "id"
    )
    val team: TeamEntity,
    val winOvertime: Int,
    val win: Int,
    val loseOvertime: Int,
    val lose: Int,
    val againstGoals: Int,
    val forGoals: Int
) {
    fun mapper() = StatisticDTO(
        country.toCountry(),
        description,
        form,
        nameGroup,
        league.mapper(),
        points,
        position,
        stage,
        team.toTeam(),
        winOvertime,
        win,
        loseOvertime,
        lose,
        againstGoals,
        forGoals
    )
}

