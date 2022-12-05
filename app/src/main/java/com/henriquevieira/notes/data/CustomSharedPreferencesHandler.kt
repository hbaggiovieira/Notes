package com.henriquevieira.notes.data

import android.content.SharedPreferences

class CustomSharedPreferencesHandler (private val sharedPreferences: SharedPreferences) : CustomSharedPreferences {
    override fun putString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    override fun getString(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }
}