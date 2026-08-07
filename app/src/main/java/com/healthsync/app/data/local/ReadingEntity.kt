package com.healthsync.app.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "readings",
    indices = [Index(value = ["patientId", "timestamp"], unique = true)]
)
data class ReadingEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val patientId: String,
    val timestamp: String,
    val heartRate: Double?,
    val oxygen: Double?,
    val activity: Double?,
    val recordedAt: Long
)
