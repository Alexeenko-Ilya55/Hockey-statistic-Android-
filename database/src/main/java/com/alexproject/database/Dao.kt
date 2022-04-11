package com.alexproject.database

import androidx.room.Dao

@Dao
interface Dao {

    /*
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: ArticleEntity)

    @Query("SELECT * FROM $TABLE_NAME LIMIT :limit OFFSET :offset")
    suspend fun getAllArticles(limit: Int, offset: Int): List<ArticleEntity>

    @Query("SELECT * FROM $TABLE_NAME WHERE bookmarkEnable")
    suspend fun getBookmarks(): List<ArticleEntity>

    @Query("DELETE FROM $TABLE_NAME")
    suspend fun deleteAll()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateElement(news: ArticleEntity)
    */
}

const val TABLE_NAME = "Table"


