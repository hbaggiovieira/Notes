package com.henriquevieira.notes.data.source

import com.henriquevieira.notes.data.model.Note
import com.henriquevieira.notes.data.room.AppDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NoteDataSourceImpl @Inject constructor(
    private val appDatabase: AppDatabase,
) : NoteDataSource {
    override suspend fun getNotes(): Flow<List<Note>> = flow {
        val result = appDatabase.noteDao().getAll()
        emit(result)
    }

    override suspend fun saveNote(note: Note) {
        appDatabase.noteDao().saveNote(note)
    }

    override suspend fun getNoteById(noteId: Int): Flow<Note> = flow {
        val result = appDatabase.noteDao().getById(noteId)
        emit(result)
    }

    override suspend fun deleteNote(note: Note) {
        appDatabase.noteDao().delete(note)
    }
}