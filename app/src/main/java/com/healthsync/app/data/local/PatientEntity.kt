package com.healthsync.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "patients")
data class PatientEntity(
    @PrimaryKey val patientId: String,
    val name: String,
    val age: Int?,
    val gender: String?,
    val notes: String?,
    val hasDevice: Boolean,
    val deviceSerialNumber: String?,
    val lastHeartRate: Int?,
    val lastOxygen: Int?,
    val lastActivity: Int?,
    val lastReadingAt: String?,
    val updatedAt: Long
)
