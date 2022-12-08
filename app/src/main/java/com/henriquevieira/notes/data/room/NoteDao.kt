package com.henriquevieira.notes.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.henriquevieira.notes.data.model.Note

@Dao
interface NoteDao {
    @Query ("SELECT * FROM note")
    fun getAll(): List<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveNote(note: Note)

    @Delete
    fun delete(note: Note)
}