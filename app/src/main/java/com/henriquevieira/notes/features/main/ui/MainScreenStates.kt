package com.henriquevieira.notes.features.main.ui

sealed class MainScreenStates {
    object OnSaveSuccess : MainScreenStates()
    object OnSaveError : MainScreenStates()
    object OnFetchError : MainScreenStates()
}