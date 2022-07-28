package com.alexproject.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.alexproject.repository.models.LeagueInfoDTO

const val TABLE_LEAGUE_INFO = "LeagueInfo"

@Entity(tableName = TABLE_LEAGUE_INFO)
data class LeagueInfoEntity(
    val countryId: Int,
    @PrimaryKey
    val id: Int,
    val logo: String,
    val name: String,
    val type: String
)

data class LeagueInfo(
    @Embedded val leagueInfoEntity: LeagueInfoEntity,
    @Relation(
        parentColumn = "countryId",
        entityColumn = "id",
    )
    val country: CountryEntity,
) {
    fun mapToDTO() = LeagueInfoDTO(
        country.mapToDTO(),
        leagueInfoEntity.id,
        leagueInfoEntity.logo,
        leagueInfoEntity.name,
        leagueInfoEntity.type
    )
}