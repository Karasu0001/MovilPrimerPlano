package com.healthsync.app.data

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "auth_session")

object AuthSessionStore {

    private val KEY_TOKEN = stringPreferencesKey("auth_token")
    private val KEY_CAREGIVER_ID = stringPreferencesKey("caregiver_id")
    private val KEY_CAREGIVER_NAME = stringPreferencesKey("caregiver_name")
    private val KEY_CAREGIVER_EMAIL = stringPreferencesKey("caregiver_email")

    private var appContext: Context? = null

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    private fun getContext(): Context = appContext
        ?: throw IllegalStateException("AuthSessionStore not initialized. Call init() from Application.onCreate().")

    suspend fun saveSession(token: String, caregiverId: String?, caregiverName: String?, caregiverEmail: String?) {
        getContext().dataStore.edit { preferences ->
            preferences[KEY_TOKEN] = token
            if (caregiverId != null) {
                preferences[KEY_CAREGIVER_ID] = caregiverId
            } else {
                preferences.remove(KEY_CAREGIVER_ID)
            }
            if (caregiverName != null) {
                preferences[KEY_CAREGIVER_NAME] = caregiverName
            } else {
                preferences.remove(KEY_CAREGIVER_NAME)
            }
            if (caregiverEmail != null) {
                preferences[KEY_CAREGIVER_EMAIL] = caregiverEmail
            } else {
                preferences.remove(KEY_CAREGIVER_EMAIL)
            }
        }
    }

    fun observeToken(): Flow<String?> {
        return getContext().dataStore.data.map { preferences ->
            preferences[KEY_TOKEN]
        }
    }

    suspend fun getToken(): String? {
        return getContext().dataStore.data.first()[KEY_TOKEN]
    }

    suspend fun clearSession() {
        getContext().dataStore.edit { preferences ->
            preferences.remove(KEY_TOKEN)
            preferences.remove(KEY_CAREGIVER_ID)
            preferences.remove(KEY_CAREGIVER_NAME)
            preferences.remove(KEY_CAREGIVER_EMAIL)
        }
    }

    suspend fun getCaregiverName(): String? {
        return getContext().dataStore.data.first()[KEY_CAREGIVER_NAME]
    }

    suspend fun getCaregiverEmail(): String? {
        return getContext().dataStore.data.first()[KEY_CAREGIVER_EMAIL]
    }

    suspend fun getCaregiverId(): String? {
        return getContext().dataStore.data.first()[KEY_CAREGIVER_ID]
    }
}