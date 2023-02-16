package com.henriquevieira.notes.features.checklist.viewmodel

import androidx.lifecycle.viewModelScope
import com.henriquevieira.notes.base.viewmodel.BaseViewModel
import com.henriquevieira.notes.data.model.CheckListItem
import com.henriquevieira.notes.domain.checklist.CheckListUseCase
import com.henriquevieira.notes.features.checklist.mvi.CheckListAction
import com.henriquevieira.notes.features.checklist.mvi.CheckListResult
import com.henriquevieira.notes.features.checklist.mvi.CheckListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckListViewModel @Inject constructor(
    private val checkListUseCase: CheckListUseCase,
) :
    BaseViewModel<CheckListAction, CheckListResult, CheckListState>() {

    override val initialState: CheckListState
        get() = CheckListState()

    override fun dispatch(action: CheckListAction) {
        when (action) {
            is CheckListAction.FetchData -> fetchData()
            is CheckListAction.ClickCheckBox -> updateItem(action.selectedItem)
            is CheckListAction.ClickAddItem -> emitResult(CheckListResult.OnClickAddItem)
            is CheckListAction.DeleteItem -> deleteItem(action.selectedItem)
            is CheckListAction.CloseButtonClick -> emitResult(CheckListResult.OnCloseButtonClick)
            is CheckListAction.ConfirmAddItem -> updateItem(CheckListItem(content = action.contentText))
        }
    }

    private fun fetchData() = viewModelScope.launch {
        val itemsList = mutableListOf<CheckListItem>()
        toggleLoading(true)
        try {
            checkListUseCase.getCheckList().collect { data ->
                itemsList.addAll(data)
                updateUiState(uiState.value.copy(itemsList = itemsList))
            }
        } catch (e: Exception) {
            emitResult(CheckListResult.OnError("Fetch error"))
        } finally {
            toggleLoading(false)
        }
    }

    private fun deleteItem(item: CheckListItem) = viewModelScope.launch (Dispatchers.IO) {
        toggleLoading(true)
        try {
            checkListUseCase.deleteItem(item)
        } catch (e: Exception) {
            emitResult(CheckListResult.OnError("Delete error"))
        } finally {
            fetchData()
        }
    }

    private fun updateItem(item: CheckListItem) = viewModelScope.launch (Dispatchers.IO) {
        toggleLoading(true)
        try {
            checkListUseCase.saveItem(item)
        } catch (e: Exception) {
            emitResult(CheckListResult.OnError("Save error"))
        } finally {
            fetchData()
        }
    }
    private fun toggleLoading(isLoading: Boolean) {
        updateUiState(uiState = uiState.value.copy(isLoading = isLoading))
        emitResult(CheckListResult.OnLoadingChanged(isLoading))
    }
}