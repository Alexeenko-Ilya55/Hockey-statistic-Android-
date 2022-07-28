package com.alexproject.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.alexproject.repository.models.StatisticDTO

const val TABLE_STATISTIC = "Statistic"

@Entity(
    tableName = TABLE_STATISTIC,
    primaryKeys = ["nameGroup", "position"],
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
    @Embedded val statisticEntity: StatisticEntity,
    @Relation(
        parentColumn = "countryId",
        entityColumn = "id"
    )
    val country: CountryEntity,
    @Relation(
        parentColumn = "leagueId",
        entityColumn = "id"
    )
    val league: LeagueEntity,
    @Relation(
        parentColumn = "teamId",
        entityColumn = "id"
    )
    val team: TeamEntity
) {
    fun mapToDTO() = StatisticDTO(
        country.mapToDTO(),
        statisticEntity.description,
        statisticEntity.form,
        statisticEntity.nameGroup,
        league.mapToDTO(),
        statisticEntity.points,
        statisticEntity.position,
        statisticEntity.stage,
        team.mapToDTO(),
        statisticEntity.winOvertime,
        statisticEntity.win,
        statisticEntity.loseOvertime,
        statisticEntity.lose,
        statisticEntity.againstGoals,
        statisticEntity.forGoals
    )
}

