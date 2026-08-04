package com.healthsync.app.data

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "patient_session")

object PatientSessionStore {

    private val KEY_PATIENT_ID = stringPreferencesKey("patient_id")
    private val KEY_PAIRING_CODE = stringPreferencesKey("pairing_code")
    private val KEY_PATIENT_NAME = stringPreferencesKey("patient_name")

    private var appContext: Context? = null

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    private fun getContext(): Context = appContext
        ?: throw IllegalStateException("PatientSessionStore not initialized. Call init() from Application.onCreate().")

    suspend fun savePatientSession(
        patientId: String,
        pairingCode: String,
        patientName: String?
    ) {
        getContext().dataStore.edit { preferences ->
            preferences[KEY_PATIENT_ID] = patientId
            preferences[KEY_PAIRING_CODE] = pairingCode
            if (patientName != null) {
                preferences[KEY_PATIENT_NAME] = patientName
            } else {
                preferences.remove(KEY_PATIENT_NAME)
            }
        }
    }

    fun observePatientId(): Flow<String?> {
        return getContext().dataStore.data.map { preferences ->
            preferences[KEY_PATIENT_ID]
        }
    }

    suspend fun getPatientId(): String? {
        return getContext().dataStore.data.first()[KEY_PATIENT_ID]
    }

    suspend fun clearPatientSession() {
        getContext().dataStore.edit { preferences ->
            preferences.remove(KEY_PATIENT_ID)
            preferences.remove(KEY_PAIRING_CODE)
            preferences.remove(KEY_PATIENT_NAME)
        }
    }
}