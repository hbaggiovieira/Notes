package com.henriquevieira.notes.features.checklist.mvi

import com.henriquevieira.notes.data.model.CheckListItem

data class CheckListState(
    val isLoading: Boolean = false,
    val itemsList: List<CheckListItem>? = null,
)
