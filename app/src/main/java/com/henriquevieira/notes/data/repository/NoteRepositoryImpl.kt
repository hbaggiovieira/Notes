package com.henriquevieira.notes.data.repository

import com.henriquevieira.notes.data.model.Note
import com.henriquevieira.notes.data.source.NoteDataSource
import com.henriquevieira.notes.domain.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepositoryImpl
@Inject constructor(
    private val noteDataSource: NoteDataSource
) : NoteRepository {
    override fun getNotes(): Flow<List<Note>> {
        return noteDataSource.getNotes()
    }

    override suspend fun saveNote(note: Note) {
        noteDataSource.saveNote(note)
    }

    override fun getNoteById(noteId: Int): Flow<Note> {
        return noteDataSource.getNoteById(noteId)
    }

    override suspend fun deleteNote(note: Note) {
        noteDataSource.deleteNote(note)
    }
}