package com.henriquevieira.notes.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.henriquevieira.notes.data.model.Note


@Database (entities = [Note::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}