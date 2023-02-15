package com.henriquevieira.notes.features.checklist.mvi

sealed class CheckListAction {
    data class ClickCheckBox (val selectedItem: CheckListState.Item): CheckListAction()
    data class DeleteItem (val selectedItem: CheckListState.Item): CheckListAction()
    object ClickAddItem: CheckListAction()
    object CloseButtonClick: CheckListAction()
}
