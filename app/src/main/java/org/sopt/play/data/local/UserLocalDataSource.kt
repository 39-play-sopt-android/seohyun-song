package org.sopt.play.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

private val Context.userDataSource: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserLocalDataSource(context: Context) {
    private val dataStore = context.applicationContext.userDataSource
    private val preferences: Flow<Preferences> = dataStore.data
        .catch { e ->
            if (e is IOException) emit(emptyPreferences())
            else throw e
        }

    val registeredEmail: Flow<String?> = preferences.map { it[Keys.EMAIL] }
    val registeredPassword: Flow<String?> = preferences.map { it[Keys.PASSWORD] }
    val isAutoLogin: Flow<Boolean> = preferences.map { it[Keys.IS_AUTO_LOGIN] ?: false }

    suspend fun saveUser(email: String, password: String) {
        dataStore.edit { prefs ->
            prefs[Keys.EMAIL] = email
            prefs[Keys.PASSWORD] = password
        }
    }

    suspend fun setAutoLogin(enabled: Boolean) {
        dataStore.edit { it[Keys.IS_AUTO_LOGIN] = enabled }
    }

    private object Keys {
        val EMAIL = stringPreferencesKey("email")
        val PASSWORD = stringPreferencesKey("password")
        val IS_AUTO_LOGIN = booleanPreferencesKey("is_auto_login")
    }
}
