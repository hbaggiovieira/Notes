package com.henriquevieira.notes.features.main.viewmodel

sealed class MainScreenEvent {
    object OnCreate: MainScreenEvent()
    object OnPrimaryColorSelected: MainScreenEvent()
    object OnRedColorSelected: MainScreenEvent()
    object OnGreenColorSelected: MainScreenEvent()
    object OnYellowColorSelected: MainScreenEvent()
    object OnBlueColorSelected: MainScreenEvent()
    object OnSaveSuccess: MainScreenEvent()
    object OnSaveError: MainScreenEvent()
    data class OnClickSaveButton(val contentText: String): MainScreenEvent()
}