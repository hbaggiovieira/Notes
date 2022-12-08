package com.henriquevieira.notes.data.source

import com.henriquevieira.notes.data.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteDataSource {
    fun getNotes(): Flow<List<Note>>

    suspend fun saveNote(note: Note)

    fun getNoteById(noteId: Int): Flow<Note>

    suspend fun deleteNote(note: Note)
}