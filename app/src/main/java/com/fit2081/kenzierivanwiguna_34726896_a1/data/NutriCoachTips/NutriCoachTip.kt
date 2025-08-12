package com.fit2081.kenzierivanwiguna_34726896_a1.data.NutriCoachTips

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.fit2081.kenzierivanwiguna_34726896_a1.data.patients.Patient

@Entity(
    tableName = "nutriCoachTips",
    foreignKeys = [ForeignKey(
        entity = Patient::class,
        parentColumns = ["userId"],
        childColumns = ["userId"]
        )
    ]
)
data class NutriCoachTip(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: String,
    val content: String,
    val time: Long
)
