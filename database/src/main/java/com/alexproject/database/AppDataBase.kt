package com.alexproject.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alexproject.database.entities.*

@Database(
    entities = [GamesEntity::class, CountryEntity::class, LeagueEntity::class, StatisticEntity::class,
        TeamEntity::class, GameEventsEntity::class, IsFavoriteGameEntity::class, IsFavoriteTeamEntity::class],
    version = 1
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun Dao(): Dao

    companion object {

        fun buildsDatabase(context: Context, dbName: String): AppDataBase {
            return Room.databaseBuilder(context, AppDataBase::class.java, dbName).build()
        }
    }
}