package com.healthsync.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [PatientEntity::class, ReadingEntity::class, AlertEntity::class],
    version = 1,
    exportSchema = false
)
abstract class HealthSyncDatabase : RoomDatabase() {
    abstract fun patientDao(): PatientDao
    abstract fun readingDao(): ReadingDao
    abstract fun alertDao(): AlertDao

    companion object {
        @Volatile
        private var instance: HealthSyncDatabase? = null

        fun getInstance(context: Context): HealthSyncDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    HealthSyncDatabase::class.java,
                    "healthsync.db"
                ).build().also { instance = it }
            }
        }
    }
}
