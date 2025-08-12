package com.fit2081.kenzierivanwiguna_34726896_a1.data.foodIntake

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.fit2081.kenzierivanwiguna_34726896_a1.data.patients.Patient
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodIntakeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(foodIntake: FoodIntake)

    @Update
    suspend fun update(foodIntake: FoodIntake)

    @Delete
    suspend fun delete(foodIntake: FoodIntake)

    @Query("DELETE FROM foodIntake")
    suspend fun deleteAllFoodIntake()

    @Query("SELECT * FROM foodIntake WHERE userId = :userId")
    suspend fun getFoodIntakeById(userId: String): FoodIntake

    @Query("SELECT * FROM foodIntake ORDER by userId ASC")
    fun getAllfoodIntake(): Flow<List<FoodIntake>>
}