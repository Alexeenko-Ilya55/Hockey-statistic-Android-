package com.alexproject.repository

import com.alexproject.domain.Repository

class RepositoryImpl(
    private val apiRepository: ApiRepository,
    private val database: Database
) : Repository {


}