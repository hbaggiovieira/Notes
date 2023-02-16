package com.henriquevieira.notes.data.source.checklist

import com.henriquevieira.notes.data.model.CheckListItem
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
}