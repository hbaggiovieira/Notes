package com.henriquevieira.notes.features.checklist.data.source

import com.henriquevieira.notes.features.checklist.data.model.CheckListItem
import kotlinx.coroutines.flow.Flow

interface CheckListDataSource {
    suspend fun getCheckList(): Flow<List<CheckListItem>>
    suspend fun saveItem(item: CheckListItem)
    suspend fun getItemById(itemId: Int): Flow<CheckListItem>
    suspend fun deleteItem(item: CheckListItem)
}