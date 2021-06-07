package com.kaiahealth.demo.core.local_storage

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kaiahealth.demo.core.model.Exercise

class LocalStorage(context: Context) {
    private val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveFavoriteExercises(exercises: List<Exercise>) {
        with(sharedPreferences.edit()) {
            val jsonValue = Gson().toJson(exercises)
            putString(KEY_FAVORITES_ITEMS, jsonValue)
            apply()
        }
    }

    fun loadFavoriteExercises(): List<Exercise>? {
        val jsonValue = sharedPreferences.getString(KEY_FAVORITES_ITEMS, null)
        val type = object : TypeToken<List<Exercise>>() {}.type
        return Gson().fromJson(jsonValue, type)
    }

    companion object {
        private const val PREFS_NAME = "local_exercises_data"
        private const val KEY_FAVORITES_ITEMS = "KEY_FAVORITES_ITEMS"
    }
}