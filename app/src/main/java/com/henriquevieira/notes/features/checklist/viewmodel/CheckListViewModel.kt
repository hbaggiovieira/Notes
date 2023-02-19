package com.henriquevieira.notes.features.checklist.viewmodel

import androidx.lifecycle.viewModelScope
import com.henriquevieira.notes.base.viewmodel.BaseViewModel
import com.henriquevieira.notes.features.checklist.data.model.CheckListItem
import com.henriquevieira.notes.features.checklist.domain.CheckListUseCase
import com.henriquevieira.notes.features.checklist.mvi.CheckListAction
import com.henriquevieira.notes.features.checklist.mvi.CheckListResult
import com.henriquevieira.notes.features.checklist.mvi.CheckListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckListViewModel @Inject constructor(
    private val checkListUseCase: CheckListUseCase,
) : BaseViewModel<CheckListAction, CheckListResult, CheckListState>() {

    override val initialState: CheckListState
        get() = CheckListState()

    override fun dispatch(action: CheckListAction) {
        when (action) {
            is CheckListAction.Init -> init()
            is CheckListAction.Close -> emitResult(CheckListResult.OnClose)
            is CheckListAction.ToggleCheck -> checkItem(
                index = action.index,
                isChecked = action.isChecked
            )
            is CheckListAction.OpenAddItem -> emitResult(CheckListResult.OnOpenAddItem)
            is CheckListAction.DeleteItem -> deleteItem(action.id)
            is CheckListAction.AddItem -> addItem(CheckListItem(content = action.contentText))
            is CheckListAction.Save -> onSaveButtonClick()
        }
    }

    private fun init() = viewModelScope.launch {
        updateUiState(uiState.value.copy(isLoading = true))
        try {
            checkListUseCase.getCheckList().collect { data ->
                updateUiState(uiState.value.copy(itemsList = data))
            }
        } catch (e: Exception) {
            emitResult(CheckListResult.OnError("Fetch error"))
        } finally {
            updateUiState(uiState.value.copy(isLoading = false))
        }
    }

    private fun deleteItem(id: Int) {
        updateUiState(uiState.value.copy(isLoading = true))
        try {
            val newList = uiState.value.itemsList.toMutableList()
            deleteById(newList, id)
            updateUiState(uiState.value.copy(itemsList = newList))
        } catch (e: Exception) {
            emitResult(CheckListResult.OnError("Delete error"))
        } finally {
            updateUiState(uiState.value.copy(isLoading = false))
        }
    }

    private fun addItem(item: CheckListItem) {
        updateUiState(uiState.value.copy(isLoading = true))
        try {
            val newList = uiState.value.itemsList.toMutableList()
            newList.add(item)
            updateUiState(uiState.value.copy(itemsList = newList))
        } catch (e: Exception) {
            emitResult(CheckListResult.OnError("Update error"))
        } finally {
            updateUiState(uiState.value.copy(isLoading = false))
        }
    }

    private fun checkItem(index: Int, isChecked: Boolean) {
        updateUiState(uiState.value.copy(isLoading = true))
        try {
            val newList = uiState.value.itemsList.toMutableList()
            newList.set(
                index = index,
                element = uiState.value.itemsList[index].copy(isChecked = isChecked)
            )
            updateUiState(uiState.value.copy(itemsList = newList))
        } catch (e: Exception) {
            emitResult(CheckListResult.OnError("Update error"))
        } finally {
            updateUiState(uiState.value.copy(isLoading = false))
        }
    }

    private fun onSaveButtonClick() = viewModelScope.launch {
        updateUiState(uiState.value.copy(isLoading = true))
        try {
            checkListUseCase.replaceDatabase(uiState.value.itemsList)
        } catch (e: Exception) {
            emitResult(CheckListResult.OnError("Save error"))
        } finally {
            updateUiState(uiState.value.copy(isLoading = false))
        }
    }

    fun deleteById(checkList: MutableList<CheckListItem>, id: Int) {
        val toDelete = checkList.find { it.id == id }
        checkList.remove(toDelete)
    }
}