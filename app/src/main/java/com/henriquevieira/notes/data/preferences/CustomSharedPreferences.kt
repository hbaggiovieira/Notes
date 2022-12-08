package com.henriquevieira.notes.data.preferences

interface CustomSharedPreferences {
    fun putString(key: String, value: String)

    fun getString(key: String, defaultValue: String): String
}