package com.healthsync.app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PatientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(patient: PatientEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(patients: List<PatientEntity>)

    @Query("SELECT * FROM patients")
    fun observeAll(): Flow<List<PatientEntity>>

    @Query("SELECT * FROM patients WHERE patientId = :id")
    fun observeById(id: String): Flow<PatientEntity?>
}
