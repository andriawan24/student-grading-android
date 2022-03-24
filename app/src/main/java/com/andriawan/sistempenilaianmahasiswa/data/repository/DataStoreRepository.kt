package com.andriawan.sistempenilaianmahasiswa.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.andriawan.sistempenilaianmahasiswa.models.User
import com.andriawan.sistempenilaianmahasiswa.utils.Constants
import com.andriawan.sistempenilaianmahasiswa.utils.Helper
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

/**
 * Create extension function to get the preferenceDataStore
 */
private val Context.dataStore by preferencesDataStore(name = Constants.PREFERENCE_NAME)

/**
 * Data store repository
 *
 * @param context - Injected from [ApplicationContext] annotation
 *
 * @author Naufal Fawwaz Andriawan
 */
class DataStoreRepository @Inject constructor(
    @ApplicationContext context: Context
) {

    /**
     * Define preference keys for night mode and user
     */
    private object PreferenceKeys {
        val NIGHT_MODE = stringPreferencesKey(Constants.PREFERENCE_DARK_MODE)
        val USER = stringPreferencesKey(Constants.PREFERENCE_USER)
    }

    private val dataStore = context.dataStore

    /**
     * Get user night mode settings
     *
     * @return [Flow] of [String] with default value of [NIGHT_MODE_NO]
     */
    fun getNightMode(): Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[PreferenceKeys.NIGHT_MODE] ?: NIGHT_MODE_NO
        }

    /**
     * Update current local user logged in
     *
     * @param user - String of user mapped by gson
     */
    suspend fun updateUser(user: String) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.USER] = user
        }
    }

    /**
     * Get logged in user locally
     *
     * @return [Flow] of [User] mapped by gson from json
     */
    fun getUser(): Flow<User?> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            val user = preferences[PreferenceKeys.USER]
            if (!user.isNullOrEmpty()) Helper.fetchStringToObject(user) else null
        }

    companion object {
        const val NIGHT_MODE_NO = "night_mode_no"
        const val NIGHT_MODE_YES = "night_mode_yes"
        const val NIGHT_MODE_SYSTEM_SETTINGS = "night_mode_system_settings"
    }
}