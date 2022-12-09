package com.henriquevieira.notes.data.source

import com.henriquevieira.notes.data.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteDataSource {
    suspend fun getNotes(): Flow<List<Note>>

    suspend fun saveNote(note: Note)

    suspend fun getNoteById(noteId: Int): Flow<Note>

    suspend fun deleteNote(note: Note)
}