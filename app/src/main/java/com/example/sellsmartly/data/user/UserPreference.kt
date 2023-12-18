package com.example.sellsmartly.data.response

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    private val token = stringPreferencesKey("token")
    private val firstTime = booleanPreferencesKey("first_time")

    fun getToken(): Flow<String> {
        return dataStore.data.map {
            it[token] ?: "null"
        }
    }

    fun isFirstTime(): Flow<Boolean> {
        return dataStore.data.map {
            it[firstTime] ?: true
        }
    }

    suspend fun saveToken(token: String) {
        dataStore.edit {
            it[this.token] = token
        }
    }

    suspend fun setFirstTime(firstTime: Boolean) {
        dataStore.edit {
            it[this.firstTime] = firstTime
        }
    }

    suspend fun deleteToken() {
        dataStore.edit {
            it.clear()
        }
    }

    companion object {
        @Volatile
        private var instance: UserPreference? = null

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference =
            instance ?: synchronized(this) {
                instance ?: UserPreference(dataStore)
            }.also { instance = it }
    }
}