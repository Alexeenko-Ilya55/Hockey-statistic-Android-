package com.alexproject.database

import com.alexproject.repository.Database
import javax.inject.Inject


class DatabaseImpl @Inject constructor(private val dao: Dao) : Database {

}