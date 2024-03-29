package com.henriquevieira.notes.domain

import com.henriquevieira.notes.data.model.Note
import javax.inject.Inject

class NoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository,
) {
    suspend fun getNotes() = noteRepository.getNotes()

    suspend fun saveNote(note: Note) = noteRepository.saveNote(note)

    suspend fun getNoteById(noteId: Int) = noteRepository.getNoteById(noteId)

    suspend fun deleteNote(note: Note) = noteRepository.deleteNote(note)
}