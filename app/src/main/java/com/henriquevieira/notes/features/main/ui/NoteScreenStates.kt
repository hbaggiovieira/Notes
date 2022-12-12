package com.henriquevieira.notes.features.main.ui

sealed class NoteScreenStates {
    object OnSaveSuccess : NoteScreenStates()
    object OnSaveError : NoteScreenStates()
    object OnLoadNoteError : NoteScreenStates()
    object OnLoadNoteSuccess : NoteScreenStates()
}