package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sankalpa_days")
data class SankalpaDay(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val cycleId: Int = 1,
    val dayNumber: Int, // 1 to 11
    val targetCount: Int = 11,
    val completedCount: Int = 0, // 0 to 11
    val isCompleted: Boolean = false,
    val dateString: String = "",
    val completedAtMillis: Long? = null,
    val notes: String = ""
)
