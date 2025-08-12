package com.fit2081.kenzierivanwiguna_34726896_a1.data.foodIntake

import android.content.Context
import com.fit2081.kenzierivanwiguna_34726896_a1.data.NutriTrackDatabase
import com.fit2081.kenzierivanwiguna_34726896_a1.data.patients.Patient
import kotlinx.coroutines.flow.Flow

class FoodIntakeRepository {
    var foodIntakeDao: FoodIntakeDao

    constructor(context: Context) {
        foodIntakeDao = NutriTrackDatabase.getDatabase(context).foodIntakeDao()
    }

    suspend fun insert(foodIntake: FoodIntake) {
        foodIntakeDao.insert(foodIntake)
    }

    suspend fun update(foodIntake: FoodIntake) {
        foodIntakeDao.update(foodIntake)
    }

    suspend fun delete(foodIntake: FoodIntake) {
        foodIntakeDao.delete(foodIntake)
    }

    suspend fun getFoodIntakeById(id: String): FoodIntake {
        return foodIntakeDao.getFoodIntakeById(id)
    }

    suspend fun deleteAllFoodIntake() {
        foodIntakeDao.deleteAllFoodIntake()
    }

    fun getAllFoodIntake(): Flow<List<FoodIntake>> = foodIntakeDao.getAllfoodIntake()
}