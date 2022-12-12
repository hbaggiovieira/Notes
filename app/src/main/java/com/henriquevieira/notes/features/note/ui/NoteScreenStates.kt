package com.henriquevieira.notes.features.note.ui

sealed class NoteScreenStates {
    object OnSaveSuccess : NoteScreenStates()
    object OnSaveError : NoteScreenStates()
    object OnLoadNoteError : NoteScreenStates()
    object OnLoadNoteSuccess : NoteScreenStates()
}