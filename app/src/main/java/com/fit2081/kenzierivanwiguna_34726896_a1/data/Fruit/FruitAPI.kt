package com.fit2081.kenzierivanwiguna_34726896_a1.data.Fruit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface FruitAPI {
    @GET("api/fruit/{name}")
    suspend fun getFruit(@Path("name") name: String): Fruit

    companion object {
        var BASE_URL = "https://www.fruityvice.com/"

        fun create(): FruitAPI {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(FruitAPI::class.java)
        }
    }
}