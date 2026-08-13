package com.healthsync.app

import android.app.Application
import com.healthsync.app.data.AuthSessionStore
import com.healthsync.app.data.DeviceIdentifier
import com.healthsync.app.data.PatientSessionStore
import com.healthsync.app.data.local.HealthSyncDb

class HealthSyncApp : Application() {
    override fun onCreate() {
        super.onCreate()
        PatientSessionStore.init(this)
        AuthSessionStore.init(this)
        HealthSyncDb.init(this)
        DeviceIdentifier.init(this)
    }
}
