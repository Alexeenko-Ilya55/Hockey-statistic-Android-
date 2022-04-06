package com.alexproject.database

import com.alexproject.repository.Database
import javax.inject.Inject


class DatabaseImpl @Inject constructor(val dao: Dao) : Database {

}