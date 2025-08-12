package com.fit2081.kenzierivanwiguna_34726896_a1.data.patients
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "patients")
data class Patient(
    @PrimaryKey
    val userId: String,
    val userName: String?,
    val userPass: String?,
    val phoneNumber: String,
    val country: String?,
    val sex: String,
    val heifaTotalScore: Float,
    val discretionaryHeifaScore: Float,
    val vegetableHeifaScore: Float,
    val fruitHeifaScore: Float,
    val fruitServeSize: Float,
    val fruitVariationScore: Float,
    val grainsCerealHeifaScore: Float,
    val wholeGrainsHeifaScore: Float,
    val meatHeifaScore: Float,
    val dairyHeifaScore: Float,
    val sodiumHeifaScore: Float,
    val alcoholHeifaScore: Float,
    val waterHeifaScore: Float,
    val sugarHeifaScore: Float,
    val satFatHeifaScore: Float,
    val unsatFatHeifaScore: Float
)
