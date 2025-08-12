package com.fit2081.kenzierivanwiguna_34726896_a1.data.foodIntake

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fit2081.kenzierivanwiguna_34726896_a1.data.patients.Patient
import com.fit2081.kenzierivanwiguna_34726896_a1.data.patients.PatientViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FoodIntakeViewModel(context: Context): ViewModel(){
    private val foodIntakeRepo = FoodIntakeRepository(context)
    val allFoodIntake: Flow<List<FoodIntake>> = foodIntakeRepo.getAllFoodIntake()

    fun insert(foodIntake: FoodIntake) = viewModelScope.launch {
        foodIntakeRepo.insert(foodIntake)
    }

    fun delete(foodIntake: FoodIntake) = viewModelScope.launch {
        foodIntakeRepo.delete(foodIntake)
    }

    fun update(foodIntake: FoodIntake) = viewModelScope.launch {
        foodIntakeRepo.update(foodIntake)
    }

    fun deleteAllFoodIntake() = viewModelScope.launch {
        foodIntakeRepo.deleteAllFoodIntake()
    }

    suspend fun getFoodIntakeById(id: String): FoodIntake {
        return foodIntakeRepo.getFoodIntakeById(id)
    }

    class FoodIntakeViewModelFactory(context: Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext

        override fun <T : ViewModel> create(modelClass: Class<T>) : T =
            FoodIntakeViewModel(context) as T
    }
}