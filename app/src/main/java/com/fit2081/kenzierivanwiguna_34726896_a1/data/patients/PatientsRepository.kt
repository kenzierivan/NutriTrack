package com.fit2081.kenzierivanwiguna_34726896_a1.data.patients

import android.content.Context
import com.fit2081.kenzierivanwiguna_34726896_a1.data.NutriTrackDatabase
import kotlinx.coroutines.flow.Flow

class PatientsRepository {
    var patientDao: PatientDao

    constructor(context: Context) {
        patientDao = NutriTrackDatabase.getDatabase(context).patientDao()
    }

    suspend fun insert(patient: Patient) {
        patientDao.insert(patient)
    }

    suspend fun delete(patient: Patient) {
        patientDao.delete(patient)
    }

    suspend fun update(patient: Patient) {
        patientDao.update(patient)
    }
    suspend fun deleteAllPatients() {
        patientDao.deleteAllPatients()
    }

    fun getAllPatient(): Flow<List<Patient>> = patientDao.getAllPatients()

    suspend fun getPatientById(id: String): Patient {
        return patientDao.getPatientById(id)
    }

    suspend fun registerUser(id: String, newName: String, phone: String, country: String, newPassword: String): Boolean {
        return patientDao.registerUser(id, newName, phone, country, newPassword) > 0
    }

    suspend fun getMaleAverageHeifaScore(): Float {
        return patientDao.getMaleAverageHeifaScore()
    }

    suspend fun getFemaleAverageHeifaScore(): Float {
        return patientDao.getFemaleAverageHeifaScore()
    }
}