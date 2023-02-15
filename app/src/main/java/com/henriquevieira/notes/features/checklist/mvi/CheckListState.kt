package com.henriquevieira.notes.features.checklist.mvi

data class CheckListState(
    val isLoading: Boolean = false,
    val itemsList: MutableList<Item>? = mutableListOf(
        Item(
            id = 0,
            isChecked = false,
            content = "Content 1"
        ), Item(
            id = 1,
            isChecked = true,
            content = "Content 2"
        )
    ),
) {
    data class Item(
        val id: Int? = null,
        var isChecked: Boolean? = false,
        val content: String? = null,
    )
}
