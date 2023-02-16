package com.henriquevieira.notes.data.source.checklist

import com.henriquevieira.notes.data.model.CheckListItem
import kotlinx.coroutines.flow.Flow

interface CheckListDataSource {
    suspend fun getCheckList(): Flow<List<CheckListItem>>
    suspend fun saveItem(item: CheckListItem)
    suspend fun getItemById(itemId: Int): Flow<CheckListItem>
    suspend fun deleteItem(item: CheckListItem)
}