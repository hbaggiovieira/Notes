package com.henriquevieira.notes.features.note.mvi

sealed class NoteResult {
    object OnCloseClick : NoteResult()
    object OnSaveSuccess : NoteResult()
    object OnSaveError : NoteResult()
    object OnLoadNoteError : NoteResult()
    object OnLoadNoteSuccess : NoteResult()
}