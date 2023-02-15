package com.henriquevieira.notes.features.checklist.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.henriquevieira.notes.base.viewmodel.BaseViewModel
import com.henriquevieira.notes.features.checklist.mvi.CheckListAction
import com.henriquevieira.notes.features.checklist.mvi.CheckListResult
import com.henriquevieira.notes.features.checklist.mvi.CheckListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckListViewModel @Inject constructor() :
    BaseViewModel<CheckListAction, CheckListResult, CheckListState>() {
    private val list = mutableStateListOf<CheckListState.Item>()

    override val initialState: CheckListState
        get() = CheckListState()

    override fun dispatch(action: CheckListAction) {
        when (action) {
            is CheckListAction.ClickCheckBox -> {
                onClickCheckbox(action.selectedItem)
            }
            is CheckListAction.ClickAddItem -> {
                onClickAddItem()
            }
            is CheckListAction.DeleteItem -> {
                onDeleteItem(action.selectedItem)
            }
            is CheckListAction.CloseButtonClick -> {
                onCloseButtonClick()
            }
        }
    }

    private fun onCloseButtonClick() = viewModelScope.launch {
        emitResult(CheckListResult.OnCloseButtonClick)
    }

    private fun onDeleteItem(selectedItem: CheckListState.Item) {
        TODO("Not yet implemented")
    }

    private fun onClickAddItem() {
        TODO("Not yet implemented")
    }

    private fun onClickCheckbox(item: CheckListState.Item) = viewModelScope.launch {
        val list = uiState.value.itemsList

        list?.find { it.id == item.id }?.isChecked = item.isChecked

        updateUiState(uiState = uiState.value.copy(itemsList = list))
    }
}