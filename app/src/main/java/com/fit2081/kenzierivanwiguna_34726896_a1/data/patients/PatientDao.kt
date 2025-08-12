package com.fit2081.kenzierivanwiguna_34726896_a1.data.patients

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PatientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(patient: Patient)

    @Update
    suspend fun update(patient: Patient)

    @Delete
    suspend fun delete(patient: Patient)

    @Query("DELETE FROM patients")
    suspend fun deleteAllPatients()

    @Query("SELECT * FROM patients ORDER by userId ASC")
    fun getAllPatients(): Flow<List<Patient>>

    @Query("SELECT * FROM patients WHERE userId= :id")
    suspend fun getPatientById(id: String): Patient

    @Query("SELECT * FROM patients WHERE userId = :id AND phoneNumber = :phoneNumber")
    suspend fun getPatientByIdAndPhone(id: String, phoneNumber: String): Patient?

    @Query("SELECT * FROM patients WHERE userId = :id AND userPass = :password")
    suspend fun getPatientByIdAndPassword(id: String, password: String): Patient?

    @Query("UPDATE patients SET userName= :newName, userPass = :newPassword, country = :country WHERE userId = :id AND phoneNumber = :phoneNumber")
    suspend fun registerUser(id: String, newName: String, phoneNumber: String, country: String, newPassword: String) : Int

    @Query("SELECT AVG(heifaTotalScore) FROM patients WHERE sex = 'Male'")
    suspend fun getMaleAverageHeifaScore(): Float

    @Query("SELECT AVG(heifaTotalScore) FROM patients WHERE sex = 'Female'")
    suspend fun getFemaleAverageHeifaScore(): Float
}