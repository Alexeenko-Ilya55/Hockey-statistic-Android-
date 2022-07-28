package com.alexproject.domain.useCases

import com.alexproject.domain.Repository
import javax.inject.Inject

class LoadCountryByIdUseCase @Inject constructor(
    private val repository: Repository
){
    suspend fun loadCountryById(countryId: Int) = repository.loadCountryById(countryId)
}