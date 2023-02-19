package com.henriquevieira.notes.features.checklist.data.room

import androidx.room.*
import com.henriquevieira.notes.features.checklist.data.model.CheckListItem

@Dao
interface CheckListDao {
    @Query("SELECT * FROM checklistitem")
    suspend fun getAll(): List<CheckListItem>

    @Query("SELECT * FROM checklistitem WHERE id = :id")
    suspend fun getById(id: Int): CheckListItem

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveItem(checkListItem: CheckListItem)

    @Delete
    suspend fun delete(checkListItem: CheckListItem)
    @Query("DELETE FROM checklistitem")
    suspend fun deleteAll()

    //ToDo fix this method
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun replaceDatabase(items: List<CheckListItem>) {
        deleteAll()

        items.forEach {
            saveItem(it)
        }
    }
}