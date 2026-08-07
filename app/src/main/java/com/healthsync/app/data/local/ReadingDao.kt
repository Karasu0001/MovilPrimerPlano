package com.healthsync.app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ReadingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(reading: ReadingEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(readings: List<ReadingEntity>)

    @Query("SELECT * FROM readings WHERE patientId = :patientId ORDER BY timestamp DESC")
    fun observeByPatient(patientId: String): Flow<List<ReadingEntity>>
}
