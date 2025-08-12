package com.fit2081.kenzierivanwiguna_34726896_a1.data.patients

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PatientViewModel(context: Context) : ViewModel() {
    private val patientRepo = PatientsRepository(context)
    val allPatients: Flow<List<Patient>> = patientRepo.getAllPatient()

    fun insert(patient: Patient) = viewModelScope.launch {
        patientRepo.insert(patient)
    }

    fun delete(patient: Patient) = viewModelScope.launch {
        patientRepo.delete(patient)
    }

    fun update(patient: Patient) = viewModelScope.launch {
        patientRepo.update(patient)
    }

    fun deleteAllPatients() = viewModelScope.launch {
        patientRepo.deleteAllPatients()
    }

    suspend fun getPatientById(id: String): Patient {
        return patientRepo.getPatientById(id)
    }

    suspend fun registerUser(id: String, newName: String, phone: String, country: String, newPassword: String): Boolean {
        return patientRepo.registerUser(id, newName, phone, country, newPassword)
    }

    suspend fun getMaleAverageHeifaScore(): Float {
        return patientRepo.getMaleAverageHeifaScore()
    }

    suspend fun getFemaleAverageHeifaScore(): Float {
        return patientRepo.getFemaleAverageHeifaScore()
    }

    class PatientViewModelFactory(context: Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext

        override fun <T : ViewModel> create(modelClass: Class<T>) : T =
            PatientViewModel(context) as T
    }
}