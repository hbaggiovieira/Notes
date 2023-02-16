package com.henriquevieira.notes.data.repository.checklist

import com.henriquevieira.notes.data.model.CheckListItem
import com.henriquevieira.notes.data.source.checklist.CheckListDataSource
import com.henriquevieira.notes.domain.checklist.CheckListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckListRepositoryImpl @Inject constructor(
    private val checkListDataSource: CheckListDataSource,
): CheckListRepository {
    override suspend fun getCheckList(): Flow<List<CheckListItem>> {
        return checkListDataSource.getCheckList()
    }

    override suspend fun saveItem(item: CheckListItem) {
        checkListDataSource.saveItem(item)
    }

    override suspend fun getItemById(itemId: Int): Flow<CheckListItem> {
        return checkListDataSource.getItemById(itemId)
    }

    override suspend fun deleteItem(item: CheckListItem) {
        checkListDataSource.deleteItem(item)
    }
}