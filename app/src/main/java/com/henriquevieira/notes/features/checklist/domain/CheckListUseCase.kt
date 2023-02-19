package com.henriquevieira.notes.features.checklist.domain

import com.henriquevieira.notes.features.checklist.data.model.CheckListItem
import com.henriquevieira.notes.features.checklist.domain.CheckListRepository
import javax.inject.Inject

class CheckListUseCase @Inject constructor(
    private val checkListRepository: CheckListRepository,
) {
    suspend fun getCheckList() = checkListRepository.getCheckList()
    suspend fun replaceDatabase(items: List<CheckListItem>) = checkListRepository.replaceDatabase(items)
}