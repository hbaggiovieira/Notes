package com.henriquevieira.notes.features.checklist.domain

import com.henriquevieira.notes.features.checklist.data.model.CheckListItem
import kotlinx.coroutines.flow.Flow

interface CheckListRepository {
    suspend fun getCheckList(): Flow<List<CheckListItem>>
    suspend fun replaceDatabase(items: List<CheckListItem>)
}