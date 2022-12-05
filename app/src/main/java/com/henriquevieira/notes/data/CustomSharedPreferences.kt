package com.henriquevieira.notes.data

interface CustomSharedPreferences {
    fun putString(key: String, value: String)

    fun getString(key: String, defaultValue: String): String
}