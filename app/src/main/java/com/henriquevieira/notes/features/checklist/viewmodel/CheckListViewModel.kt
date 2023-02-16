package com.henriquevieira.notes.features.checklist.viewmodel

import androidx.compose.runtime.mutableStateListOf
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
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CheckListViewModel @Inject constructor(
    private val checkListUseCase: CheckListUseCase,
) :
    BaseViewModel<CheckListAction, CheckListResult, CheckListState>() {
    private val list = mutableStateListOf<CheckListItem>()

    override val initialState: CheckListState
        get() = CheckListState()

    override fun dispatch(action: CheckListAction) {
        when (action) {
            is CheckListAction.FetchData -> fetchData()
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
            is CheckListAction.SaveButtonClick -> {
                onSaveButtonClick()
            }
            is CheckListAction.ConfirmAddItem -> {
                onConfirmAddItem(action.contentText)
            }
        }
    }

    private fun onClickAddItem() = viewModelScope.launch {
        emitResult(CheckListResult.OnClickAddItem)
    }

    private fun onSaveButtonClick() = viewModelScope.launch {
        toggleLoading(true)
        try {
            list.forEach {
                checkListUseCase.saveItem(it)
            }
        } catch (e: Exception) {
            //ToDo
        } finally {
            toggleLoading(false)
        }
    }

    private fun onCloseButtonClick() = viewModelScope.launch {
        emitResult(CheckListResult.OnCloseButtonClick)
    }

    private fun onDeleteItem(selectedItem: CheckListItem) = viewModelScope.launch {
        toggleLoading(true)
        try {
            list.remove(selectedItem)
            checkListUseCase.deleteItem(selectedItem)

            fetchData()
        } catch (e: Exception) {
            //ToDo
        }
    }

    private fun onConfirmAddItem(text: String) = viewModelScope.launch {
        toggleLoading(true)

        try {
            list.add(CheckListItem(content = text))

            updateUiState(uiState.value.copy(itemsList = list))
        } catch (e: Exception) {
            //ToDo
        } finally {
            toggleLoading(false)
        }
    }

    private fun onClickCheckbox(item: CheckListItem) = viewModelScope.launch {
        toggleLoading(true)
        try {
            checkListUseCase.saveItem(item)

            list.find { it.id == item.id }?.isChecked = item.isChecked

            updateUiState(uiState = uiState.value.copy(itemsList = list))
        } catch (e: Exception) {
            //ToDo
        } finally {
            toggleLoading(false)
        }
    }

    private fun fetchData() = viewModelScope.launch (Dispatchers.IO) {
        toggleLoading(true)

        try {
            list.clear()

            checkListUseCase.getCheckList().collect {
                list.addAll(it)
            }

            withContext(Dispatchers.Main) {
                updateUiState(uiState.value.copy(itemsList = list))
            }
        } catch (e: Exception) {
            //ToDo
        } finally {
            toggleLoading(false)
        }
    }

    private fun toggleLoading(isLoading: Boolean) {
        updateUiState(uiState = uiState.value.copy(isLoading = isLoading))

        emitResult(CheckListResult.OnLoadingChanged(isLoading))
    }
}