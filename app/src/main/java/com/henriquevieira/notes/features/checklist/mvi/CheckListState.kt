package com.henriquevieira.notes.features.checklist.mvi

import com.henriquevieira.notes.features.checklist.data.model.CheckListItem

data class CheckListState(
    val isLoading: Boolean = false,
    val itemsList: List<CheckListItem> = listOf(),
    val addItemDialogState: Boolean = false,
)
