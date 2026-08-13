package com.healthsync.app.data

import android.content.Context
import android.provider.Settings

object DeviceIdentifier {

    private var appContext: Context? = null

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    private fun getContext(): Context = appContext
        ?: throw IllegalStateException("DeviceIdentifier not initialized. Call init() from Application.onCreate().")

    // Identificador estable por instalación (cambia solo si se desinstala/reinstala
    // la app o se resetea el equipo). Se usa como serialNumber al vincular un
    // dispositivo, para que cada teléfono/instalación quede identificado como algo
    // distinto ante el backend en vez de mandar siempre el mismo valor.
    fun getId(): String {
        return Settings.Secure.getString(getContext().contentResolver, Settings.Secure.ANDROID_ID)
            ?: "unknown-device"
    }
}
