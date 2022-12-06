package com.henriquevieira.notes.features.main.ui

sealed class MainNoteState {
    object OnSaveSuccess: MainNoteState()
    object OnSaveError: MainNoteState()
}