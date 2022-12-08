package com.henriquevieira.notes.data.room

import androidx.room.*
import com.henriquevieira.notes.data.model.Note

@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    suspend fun getAll(): List<Note>

    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getById(id: Int): Note

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNote(note: Note)

    @Delete
    suspend fun delete(note: Note)
}