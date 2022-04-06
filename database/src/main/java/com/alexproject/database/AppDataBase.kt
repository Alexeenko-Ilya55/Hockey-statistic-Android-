package com.alexproject.database

import android.content.Context
import androidx.room.*


@Entity
data class Test(
    @PrimaryKey
    val l: String
)


@Database(
    entities = [Test::class], version = 1
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun Dao(): Dao

    companion object {

        fun buildsDatabase(context: Context, dbName: String): AppDataBase {
            return Room.databaseBuilder(context, AppDataBase::class.java, dbName).build()
        }
    }
}