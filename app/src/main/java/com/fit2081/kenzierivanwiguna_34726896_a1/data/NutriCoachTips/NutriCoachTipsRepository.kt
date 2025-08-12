package com.fit2081.kenzierivanwiguna_34726896_a1.data.NutriCoachTips

import android.content.Context
import com.fit2081.kenzierivanwiguna_34726896_a1.data.NutriTrackDatabase
import com.fit2081.kenzierivanwiguna_34726896_a1.data.patients.Patient
import kotlinx.coroutines.flow.Flow

class NutriCoachTipsRepository {
    var nutriCoachTipDao: NutriCoachTipDao

    constructor(context: Context) {
        nutriCoachTipDao = NutriTrackDatabase.getDatabase(context).nutriCoachTipDao()
    }

    suspend fun insert(nutriCoachTip: NutriCoachTip) {
        nutriCoachTipDao.insert(nutriCoachTip)
    }

    fun getTips(userId: String): Flow<List<NutriCoachTip>> {
        return nutriCoachTipDao.getTips(userId)
    }
}