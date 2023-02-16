package com.henriquevieira.notes.features.checklist.mvi

import com.henriquevieira.notes.data.model.CheckListItem

sealed class CheckListAction {

//    object SaveButtonClick: CheckListAction()
    object Init: CheckListAction()
    data class ToggleCheck (val index: Int, val isChecked: Boolean): CheckListAction()
    data class DeleteItem (val index: Int): CheckListAction()
    object OpenAddItem: CheckListAction()
    data class AddItem(val contentText: String): CheckListAction()
    object Close: CheckListAction()
    object Save: CheckListAction()
}
