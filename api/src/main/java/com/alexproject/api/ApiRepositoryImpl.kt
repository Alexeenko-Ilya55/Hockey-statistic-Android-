package com.alexproject.api

import com.alexproject.repository.ApiRepository
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(val apiService: ApiService): ApiRepository {

}