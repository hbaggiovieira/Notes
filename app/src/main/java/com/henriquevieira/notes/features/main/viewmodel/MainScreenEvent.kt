package com.henriquevieira.notes.features.main.viewmodel

sealed class MainScreenEvent {
    object OnPrimaryColorSelected: MainScreenEvent()
    object OnRedColorSelected: MainScreenEvent()
    object OnGreenColorSelected: MainScreenEvent()
    object OnYellowColorSelected: MainScreenEvent()
    object OnBlueColorSelected: MainScreenEvent()
    object OnSaveSuccess: MainScreenEvent()
    object OnSaveError: MainScreenEvent()
    object OnClickClearButton: MainScreenEvent()
    data class OnClickSaveButton(val contentText: String): MainScreenEvent()
}