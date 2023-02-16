package com.henriquevieira.notes.features.checklist.mvi

import com.henriquevieira.notes.data.model.CheckListItem

sealed class CheckListAction {

    object SaveButtonClick: CheckListAction()
    object FetchData: CheckListAction()
    data class ClickCheckBox (val selectedItem: CheckListItem): CheckListAction()
    data class DeleteItem (val selectedItem: CheckListItem): CheckListAction()
    object ClickAddItem: CheckListAction()
    object CloseButtonClick: CheckListAction()
}
