// file: WeatherRepository.kt
package com.example.catashtope

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "settings")

class WeatherRepository(private val context: Context) {

    private val weatherDao = AppDatabase.getDatabase(context).weatherDao()
    private val dataStore = context.dataStore

    companion object {
        val LAST_UPDATED_KEY = longPreferencesKey("last_updated")
    }

    val weatherData: Flow<WeatherResponse?> = weatherDao.getWeatherData()
    val lastUpdatedTimestamp: Flow<Long?> = dataStore.data.map { it[LAST_UPDATED_KEY] }
    val allWeatherData: Flow<List<WeatherResponse>> = weatherDao.getAllWeatherData()


    suspend fun refreshWeatherData() {
        try {
            Log.d("WeatherRepository", "Fetching weather data from API...")
            val response = WeatherApi.retrofitService.getWeatherData()
            if (response.isSuccessful && response.body() != null) {
                val newData = response.body()!!
                Log.d("WeatherRepository", "API Success: $newData")

                weatherDao.insertOrUpdate(newData)
                Log.d("WeatherRepository", "Data inserted to DB with id: ${newData.id}")

                dataStore.edit { it[LAST_UPDATED_KEY] = System.currentTimeMillis() }
            } else {
                Log.e("WeatherRepository", "API Failed: ${response.code()} - ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            Log.e("WeatherRepository", "Exception during API call: ${e.message}")
            throw e // rethrow so ViewModel can catch it and update UI
        }
    }
}
