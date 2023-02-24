package ict2105.team02.application.repo

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

// Constant DataStore name
private const val USER_PREFERENCES_NAME = "user_preferences"

// Constants for keys
private const val LAYOUT_TYPE = "schedule_layout_is_month"

// This data class holds the preference settings that are saved in the DataStore. It is exposed via the Flow interface.
data class UserPreferences(val scheduleLayoutType: Boolean)

// declare the Preferences DataStore in the top-level
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_PREFERENCES_NAME)

/**
 * Class that handles saving and retrieving user preferences, utilizing Preferences DataStore. This
 * class may be utilized in either the ViewModel or an Activity, depending on what preferences are
 * being saved.
 */
class UserPreferencesRepository(context: Context) {
    // object for defining PreferencesKeys
    private object PreferencesKeys {
        val scheduleLayoutType: Preferences.Key<Boolean> = booleanPreferencesKey(LAYOUT_TYPE)
    }

    // allows getting of the user preferences flow
    val userPreferencesFlow: Flow<UserPreferences> = context.dataStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            // Get our show completed value, defaulting to true if not set:
            val layoutType = preferences[PreferencesKeys.scheduleLayoutType] ?: true
            UserPreferences(layoutType)
        }


    // updates layout type to boolean provided
    suspend fun updateLayoutType(layoutType: Boolean, context: Context) {
        Log.d(TAG, object {}.javaClass.enclosingMethod.name + ": " + layoutType.toString())
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.scheduleLayoutType] = layoutType
        }
    }

    // singularity point
    companion object {
        // The usual for debugging
        private val TAG: String = "UserPreferencesRepository"

        // Boilerplate-y code for singleton: the private reference to this self
        @Volatile
        private var INSTANCE: UserPreferencesRepository? = null

        /**
         * Boilerplate-y code for singleton: to ensure only a single copy is ever present
         * @param context to init the datastore
         */
        fun getInstance(context: Context): UserPreferencesRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE?.let {
                    return it
                }

                val instance = UserPreferencesRepository(context)
                INSTANCE = instance
                instance
            }
        }
    }
}