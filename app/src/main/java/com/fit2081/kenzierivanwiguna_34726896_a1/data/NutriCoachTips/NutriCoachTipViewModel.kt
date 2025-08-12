package com.fit2081.kenzierivanwiguna_34726896_a1.data.NutriCoachTips

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fit2081.kenzierivanwiguna_34726896_a1.data.patients.Patient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NutriCoachTipViewModel(context: Context) : ViewModel() {
    private val nutriCoachTipRepo = NutriCoachTipsRepository(context)

    fun insert(nutriCoachTip: NutriCoachTip) = viewModelScope.launch {
        nutriCoachTipRepo.insert(nutriCoachTip)
    }

    fun getTips(userId: String): Flow<List<NutriCoachTip>> {
        return nutriCoachTipRepo.getTips(userId)
    }

    class NutriCoachTipModelFactory(context: Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext

        override fun <T: ViewModel> create(modelClass: Class<T>) : T =
            NutriCoachTipViewModel(context) as T
    }
}