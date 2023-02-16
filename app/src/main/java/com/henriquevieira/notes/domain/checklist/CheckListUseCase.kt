package com.henriquevieira.notes.domain.checklist

import com.henriquevieira.notes.data.model.CheckListItem
import javax.inject.Inject

class CheckListUseCase @Inject constructor(
    private val checkListRepository: CheckListRepository,
) {
    suspend fun getCheckList() = checkListRepository.getCheckList()
    suspend fun saveItem(item: CheckListItem) = checkListRepository.saveItem(item)
    suspend fun getItemById(itemId: Int) = checkListRepository.getItemById(itemId)
    suspend fun deleteItem(item: CheckListItem) = checkListRepository.deleteItem(item)
}