package com.henriquevieira.notes.features.checklist.data.room

import androidx.room.*
import com.henriquevieira.notes.features.checklist.data.model.CheckListItem

@Dao
interface CheckListDao {
    @Query("SELECT * FROM checklistitem")
    suspend fun getAll(): List<CheckListItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveItem(checkListItem: CheckListItem)

    @Query("DELETE FROM checklistitem")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun replaceDatabase(items: List<CheckListItem>) {
        deleteAll()

        items.forEach { saveItem(it) }
    }
}