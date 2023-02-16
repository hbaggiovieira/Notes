package com.henriquevieira.notes.domain.checklist

import com.henriquevieira.notes.data.model.CheckListItem
import kotlinx.coroutines.flow.Flow

interface CheckListRepository {
    suspend fun getCheckList(): Flow<List<CheckListItem>>
    suspend fun saveItem(item: CheckListItem)
    suspend fun getItemById(itemId: Int): Flow<CheckListItem>
    suspend fun deleteItem(item: CheckListItem)
}