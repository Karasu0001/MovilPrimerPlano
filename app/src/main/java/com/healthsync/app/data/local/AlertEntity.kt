package com.healthsync.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alerts")
data class AlertEntity(
    @PrimaryKey val id: String,
    val patientId: String,
    val patientName: String,
    val type: String,
    val message: String,
    val severity: Int,
    val isRead: Boolean,
    val createdAt: String
)
