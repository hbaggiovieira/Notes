package com.henriquevieira.notes.data.room

import androidx.room.*
import com.henriquevieira.notes.data.model.Note

@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    fun getAll(): List<Note>

    @Query("SELECT * FROM note WHERE id = :id")
    fun getById(id: Int): Note

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveNote(note: Note)

    @Delete
    fun delete(note: Note)
}