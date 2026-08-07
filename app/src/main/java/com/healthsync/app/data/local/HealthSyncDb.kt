package com.healthsync.app.data.local

import android.content.Context

object HealthSyncDb {
    private var db: HealthSyncDatabase? = null

    fun init(context: Context) {
        db = HealthSyncDatabase.getInstance(context)
    }

    fun patientDao(): PatientDao = db!!.patientDao()
    fun readingDao(): ReadingDao = db!!.readingDao()
    fun alertDao(): AlertDao = db!!.alertDao()
}
