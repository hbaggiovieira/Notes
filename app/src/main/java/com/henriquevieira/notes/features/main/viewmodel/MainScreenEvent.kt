package com.henriquevieira.notes.features.main.viewmodel

sealed class MainScreenEvent {
    object OnSuccess: MainScreenEvent()
    object OnError: MainScreenEvent()
    object OnPrimaryColorSelected: MainScreenEvent()
    object OnRedColorSelected: MainScreenEvent()
    object OnGreenColorSelected: MainScreenEvent()
    object OnYellowColorSelected: MainScreenEvent()
    object OnBlueColorSelected: MainScreenEvent()
}