package com.fit2081.kenzierivanwiguna_34726896_a1.data.foodIntake

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.fit2081.kenzierivanwiguna_34726896_a1.data.patients.Patient

@Entity(
    tableName = "foodIntake",
    foreignKeys = [ForeignKey(
        entity = Patient::class,
        parentColumns = ["userId"],
        childColumns = ["userId"]
        )
    ]
)
data class FoodIntake(
    @PrimaryKey
    val userId: String,
    val eatFruits: Boolean,
    val eatVegetables: Boolean,
    val eatGrains: Boolean,
    val eatMeats: Boolean,
    val eatSeafood: Boolean,
    val eatPoultry: Boolean,
    val eatFish: Boolean,
    val eatEggs: Boolean,
    val eatNuts: Boolean,
    val persona: String,
    val mealTime: String,
    val sleepTime: String,
    val wakeTime: String
)
