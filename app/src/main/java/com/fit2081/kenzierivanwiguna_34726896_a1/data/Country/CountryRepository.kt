package com.fit2081.kenzierivanwiguna_34726896_a1.data.Country

class CountryRepository {
    private val countryAPI = CountryAPI.create()

    suspend fun getAllCountries(): List<Country> {
        return countryAPI.getAllCountries()
    }
}