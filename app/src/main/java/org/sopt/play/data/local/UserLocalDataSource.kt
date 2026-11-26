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
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

private val Context.userDataSource: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserLocalDataSource(context: Context) {
    private val dataStore = context.applicationContext.userDataSource
    private val preferences: Flow<Preferences> = dataStore.data
        .catch { e ->
            if (e is IOException) emit(emptyPreferences())
            else throw e
        }

    val registeredName: Flow<String?> = preferences
        .map { it[Keys.NAME] }
        .distinctUntilChanged()

    val registeredEmail: Flow<String?> = preferences
        .map { it[Keys.EMAIL] }
        .distinctUntilChanged()
    val registeredPassword: Flow<String?> = preferences
        .map { it[Keys.PASSWORD] }
        .distinctUntilChanged()
    val isAutoLogin: Flow<Boolean> = preferences
        .map { it[Keys.IS_AUTO_LOGIN] ?: false }
        .distinctUntilChanged()

    val authorizationCode: Flow<String?> = preferences
        .map { it[Keys.AUTHORIZATION_CODE] }
        .distinctUntilChanged()

    suspend fun saveUser(name: String, email: String, password: String): Result<Unit> = runCatching {
        dataStore.edit { prefs ->
            prefs[Keys.NAME] = name
            prefs[Keys.EMAIL] = email
            prefs[Keys.PASSWORD] = password
            prefs.remove(Keys.AUTHORIZATION_CODE)
        }
    }

    suspend fun setAutoLogin(enabled: Boolean): Result<Unit> = runCatching {
        dataStore.edit { it[Keys.IS_AUTO_LOGIN] = enabled }
    }

    suspend fun saveAuthorizationCode(code: String): Result<Unit> = runCatching {
        dataStore.edit { it[Keys.AUTHORIZATION_CODE] = code }
    }

    private object Keys {
        val NAME = stringPreferencesKey("name")
        val EMAIL = stringPreferencesKey("email")
        val PASSWORD = stringPreferencesKey("password")
        val IS_AUTO_LOGIN = booleanPreferencesKey("is_auto_login")
        val AUTHORIZATION_CODE = stringPreferencesKey("authorization_code")
    }
}
