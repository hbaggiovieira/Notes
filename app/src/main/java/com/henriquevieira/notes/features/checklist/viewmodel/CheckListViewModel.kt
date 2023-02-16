package com.henriquevieira.notes.features.checklist.viewmodel

import androidx.lifecycle.viewModelScope
import com.henriquevieira.notes.base.viewmodel.BaseViewModel
import com.henriquevieira.notes.data.model.CheckListItem
import com.henriquevieira.notes.domain.checklist.CheckListUseCase
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

    private val mList = mutableListOf<CheckListItem>()

    override val initialState: CheckListState
        get() = CheckListState()

    override fun dispatch(action: CheckListAction) {
        when (action) {
            is CheckListAction.Init -> init()
            is CheckListAction.Close -> onClose()
            is CheckListAction.ToggleCheck -> checkItem(
                index = action.index,
                isChecked = action.isChecked
            )
            is CheckListAction.OpenAddItem -> emitResult(CheckListResult.OnOpenAddItem)
            is CheckListAction.DeleteItem -> deleteItem(action.index)
            is CheckListAction.AddItem -> addItem(CheckListItem(content = action.contentText))
            is CheckListAction.Save -> onSaveButtonClick()
        }
    }

    private fun init() = viewModelScope.launch {
        updateUiState(uiState.value.copy(isLoading = true))
        try {
            checkListUseCase.getCheckList().collect { data -> mList.addAll(data) }
            updateUiState(uiState.value.copy(itemsList = mList))
        } catch (e: Exception) {
            emitResult(CheckListResult.OnError("Fetch error"))
        } finally {
            updateUiState(uiState.value.copy(isLoading = false))
        }
    }

    private fun deleteItem(index: Int) {
        updateUiState(uiState.value.copy(isLoading = true))
        try {
            mList.removeAt(index = index)
//            checkListUseCase.deleteItem(item)
            updateUiState(uiState.value.copy(itemsList = mList))
        } catch (e: Exception) {
            emitResult(CheckListResult.OnError("Delete error"))
        } finally {
            updateUiState(uiState.value.copy(isLoading = false))
        }
    }

    private fun addItem(item: CheckListItem) {
        updateUiState(uiState.value.copy(isLoading = true))
        try {
            mList.add(item)
            updateUiState(uiState.value.copy(itemsList = mList))
        } catch (e: Exception) {
            emitResult(CheckListResult.OnError("Update error"))
        } finally {
            updateUiState(uiState.value.copy(isLoading = false))
        }
    }

    private fun checkItem(index: Int, isChecked: Boolean) {
        updateUiState(uiState.value.copy(isLoading = true))
        try {
            mList.set(index = index, element = mList[index].copy(isChecked = isChecked))
            updateUiState(uiState.value.copy(itemsList = mList))
        } catch (e: Exception) {
            emitResult(CheckListResult.OnError("Update error"))
        } finally {
            updateUiState(uiState.value.copy(isLoading = false))
        }
    }

    private fun onSaveButtonClick() = viewModelScope.launch {
        updateUiState(uiState.value.copy(isLoading = true))
        try {
            mList.forEach {
                if (!uiState.value.itemsList.contains(it)) {
                    checkListUseCase.deleteItem(it)
                }

                checkListUseCase.saveItem(it)
                updateUiState(uiState.value.copy(itemsList = mList))
            }
        } catch (e: Exception) {
            emitResult(CheckListResult.OnError("Save error"))
        } finally {
            updateUiState(uiState.value.copy(isLoading = false))
        }
    }

    private fun onClose() = viewModelScope.launch {
//        updateUiState(uiState.value.copy(isLoading = true))
//        try {
//            mList.forEach {
//                checkListUseCase.saveItem(it)
//            }
//        } catch (e: Exception) {
//            emitResult(CheckListResult.OnError("Save error"))
//        } finally {
//            emitResult(CheckListResult.OnClose)
//            updateUiState(uiState.value.copy(isLoading = false))
//        }
    }
}