package com.henriquevieira.notes.features.checklist.data.source

import com.henriquevieira.notes.features.checklist.data.model.CheckListItem
import com.henriquevieira.notes.data.room.AppDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CheckListDataSourceImpl @Inject constructor(
    private val appDataBase: AppDatabase,
) : CheckListDataSource {
    override suspend fun getCheckList(): Flow<List<CheckListItem>> = flow {
        val result = appDataBase.checkListDao().getAll()
        emit(result)
    }

    override suspend fun saveItem(item: CheckListItem) {
        appDataBase.checkListDao().saveItem(item)
    }

    override suspend fun getItemById(itemId: Int): Flow<CheckListItem> = flow {
        val result = appDataBase.checkListDao().getById(itemId)
        emit(result)
    }

    override suspend fun deleteItem(item: CheckListItem) {
        appDataBase.checkListDao().delete(item)
    }
    override suspend fun replaceDatabase(items: List<CheckListItem>) {
        appDataBase.checkListDao().replaceDatabase(items)
    }
}