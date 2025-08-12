package com.fit2081.kenzierivanwiguna_34726896_a1.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fit2081.kenzierivanwiguna_34726896_a1.data.NutriCoachTips.NutriCoachTip
import com.fit2081.kenzierivanwiguna_34726896_a1.data.NutriCoachTips.NutriCoachTipDao
import com.fit2081.kenzierivanwiguna_34726896_a1.data.patients.Patient
import com.fit2081.kenzierivanwiguna_34726896_a1.data.patients.PatientDao
import com.fit2081.kenzierivanwiguna_34726896_a1.data.foodIntake.FoodIntake
import com.fit2081.kenzierivanwiguna_34726896_a1.data.foodIntake.FoodIntakeDao

@Database(entities = [Patient::class, FoodIntake::class, NutriCoachTip::class], version = 1, exportSchema = false)
abstract class NutriTrackDatabase: RoomDatabase() {
    abstract fun patientDao(): PatientDao
    abstract fun foodIntakeDao(): FoodIntakeDao
    abstract fun nutriCoachTipDao(): NutriCoachTipDao

    companion object {
        @Volatile
        private var Instance: NutriTrackDatabase? = null

        fun getDatabase(context: Context): NutriTrackDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, NutriTrackDatabase::class.java, "nutritrack_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}