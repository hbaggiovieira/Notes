package com.henriquevieira.notes.features.checklist.data.repository

import com.henriquevieira.notes.features.checklist.data.model.CheckListItem
import com.henriquevieira.notes.features.checklist.data.source.CheckListDataSource
import com.henriquevieira.notes.features.checklist.domain.CheckListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckListRepositoryImpl @Inject constructor(
    private val checkListDataSource: CheckListDataSource,
): CheckListRepository {
    override suspend fun getCheckList(): Flow<List<CheckListItem>> {
        return checkListDataSource.getCheckList()
    }

    override suspend fun replaceDatabase(items: List<CheckListItem>) {
        checkListDataSource.replaceDatabase(items)
    }
}