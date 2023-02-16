package com.henriquevieira.notes.features.checklist.mvi

import com.henriquevieira.notes.data.model.CheckListItem

sealed class CheckListAction {

//    object SaveButtonClick: CheckListAction()
    data class FetchData(val isInit: Boolean = false): CheckListAction()
    data class ClickCheckBox (val selectedItem: CheckListItem): CheckListAction()
    data class DeleteItem (val selectedItem: CheckListItem): CheckListAction()
    object ClickAddItem: CheckListAction()
    data class ConfirmAddItem(val contentText: String): CheckListAction()
    object CloseButtonClick: CheckListAction()
}
