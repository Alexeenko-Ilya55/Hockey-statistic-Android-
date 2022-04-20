package com.alexproject.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Relation
import com.alexproject.repository.models.TeamInfoDTO

const val TABLE_TEAM_INFO = "teamInfo"

@Entity(
    tableName = TABLE_TEAM_INFO,
    foreignKeys = [
        ForeignKey(
            entity = CountryEntity::class,
            parentColumns = ["id"],
            childColumns = ["countryId"],
            onDelete = ForeignKey.NO_ACTION,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
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
    fun mapper() = TeamInfoDTO(
        country.toCountry(),
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



