package com.henriquevieira.notes.features.checklist.domain

import com.henriquevieira.notes.features.checklist.data.model.CheckListItem
import com.henriquevieira.notes.features.checklist.domain.CheckListRepository
import javax.inject.Inject

class CheckListUseCase @Inject constructor(
    private val checkListRepository: CheckListRepository,
) {
    suspend fun getCheckList() = checkListRepository.getCheckList()
    suspend fun saveItem(item: CheckListItem) = checkListRepository.saveItem(item)
    suspend fun getItemById(itemId: Int) = checkListRepository.getItemById(itemId)
    suspend fun deleteItem(item: CheckListItem) = checkListRepository.deleteItem(item)
    suspend fun replaceDatabase(items: List<CheckListItem>) = checkListRepository.replaceDatabase(items)
}