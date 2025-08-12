package com.fit2081.kenzierivanwiguna_34726896_a1.data.NutriCoachTips

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NutriCoachTipDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(NutriCoachTip: NutriCoachTip)

    @Query("SELECT * FROM nutriCoachTips WHERE userId = :userId ORDER BY time DESC")
    fun getTips(userId: String): Flow<List<NutriCoachTip>>
}