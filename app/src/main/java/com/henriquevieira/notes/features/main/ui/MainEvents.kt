package com.henriquevieira.notes.features.main.ui

sealed class MainEvents {
    object OnPrimaryColorSelected: MainEvents()
    object OnRedColorSelected: MainEvents()
    object OnGreenColorSelected: MainEvents()
    object OnYellowColorSelected: MainEvents()
    object OnBlueColorSelected: MainEvents()
    object OnClickClearButton: MainEvents()
    data class OnClickSaveButton(val contentText: String): MainEvents()
}