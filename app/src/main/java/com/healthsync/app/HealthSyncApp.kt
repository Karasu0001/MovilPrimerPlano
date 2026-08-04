package com.healthsync.app

import android.app.Application
import com.healthsync.app.data.AuthSessionStore
import com.healthsync.app.data.PatientSessionStore

class HealthSyncApp : Application() {
    override fun onCreate() {
        super.onCreate()
        PatientSessionStore.init(this)
        AuthSessionStore.init(this)
    }
}
