package com.henriquevieira.notes.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.henriquevieira.notes.features.checklist.data.model.CheckListItem
import com.henriquevieira.notes.data.model.Note
import com.henriquevieira.notes.features.checklist.data.room.CheckListDao

@Database(entities = [Note::class, CheckListItem::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun checkListDao(): CheckListDao
}