package com.alexproject.database.entities

import androidx.room.Entity
import androidx.room.Relation
import com.alexproject.repository.models.TeamInfoDTO

const val TABLE_TEAM_INFO = "teamInfo"

@Entity(tableName = TABLE_TEAM_INFO)
data class TeamInfoEntity(
    val countryId: Int,
    val founded: Int,
    val id: Int,
    val logo: String,
    val name: String,
    val national: Boolean,
    val capacityArena: Int,
    val locationArena: String,
    val nameArena: String
)

data class TeamInfo(
    @Relation(
        parentColumn = "countryId",
        entityColumn = "id"
    )
    val country: CountryEntity,
    val founded: Int,
    val id: Int,
    val logo: String,
    val name: String,
    val national: Boolean,
    val capacityArena: Int,
    val locationArena: String,
    val nameArena: String
) {
    fun mapToDTO() = TeamInfoDTO(
        country.mapToDTO(),
        founded,
        id,
        logo,
        name,
        national,
        capacityArena,
        locationArena,
        nameArena
    )
}



