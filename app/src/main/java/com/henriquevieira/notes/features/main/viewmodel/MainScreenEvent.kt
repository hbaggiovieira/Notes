package com.henriquevieira.notes.features.main.viewmodel

sealed class MainScreenEvent {
    object OnSuccess: MainScreenEvent()
    object OnError: MainScreenEvent()
}