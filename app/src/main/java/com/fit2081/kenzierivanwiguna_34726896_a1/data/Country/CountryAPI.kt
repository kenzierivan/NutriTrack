package com.fit2081.kenzierivanwiguna_34726896_a1.data.Country

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface CountryAPI {
    @GET("all?fields=name")
    suspend fun getAllCountries(): List<Country>

    companion object {
        var BASE_URL = "https://restcountries.com/v3.1/"
        fun create(): CountryAPI {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(CountryAPI.BASE_URL)
                .build()
            return retrofit.create(CountryAPI::class.java)
        }
    }
}