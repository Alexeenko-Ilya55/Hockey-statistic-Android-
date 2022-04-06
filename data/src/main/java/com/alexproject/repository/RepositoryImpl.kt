package com.alexproject.repository

import com.alexproject.domain.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    apiRepository: ApiRepository,
    database: Database
) : Repository {

}