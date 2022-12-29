package com.henriquevieira.notes.features.note.ui

sealed class NoteResults {
    object OnSaveSuccess : NoteResults()
    object OnSaveError : NoteResults()
    object OnLoadNoteError : NoteResults()
    object OnLoadNoteSuccess : NoteResults()
}