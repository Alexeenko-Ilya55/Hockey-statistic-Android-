package com.alexproject.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.alexproject.repository.models.LeagueInfoDTO

const val TABLE_LEAGUE_INFO = "LeagueInfo"

@Entity(
    tableName = TABLE_LEAGUE_INFO,
    foreignKeys = [
        ForeignKey(
            entity = CountryEntity::class,
            parentColumns = ["id"],
            childColumns = ["countryId"]
        )
    ]
)
data class LeagueInfoEntity(
    val countryId: Int,
    @PrimaryKey
    val id: Int,
    val logo: String,
    val name: String,
    val type: String
)

data class LeagueInfo(
    @Relation(
        parentColumn = "countryId",
        entityColumn = "id"
    )
    val country: CountryEntity,
    val id: Int,
    val logo: String,
    val name: String,
    val type: String
) {
    fun mapper() = LeagueInfoDTO(
        country.toCountry(),
        id,
        logo,
        name,
        type
    )
}