package com.fit2081.kenzierivanwiguna_34726896_a1.data.Fruit

class FruitRepository {
    private val fruitAPI = FruitAPI.create()

    suspend fun getFruit(name: String): Fruit {
        return fruitAPI.getFruit(name)
    }
}