package com.henriquevieira.notes.features.home.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.henriquevieira.notes.base.viewmodel.BaseViewModel
import com.henriquevieira.notes.data.model.Note
import com.henriquevieira.notes.domain.NoteUseCase
import com.henriquevieira.notes.features.home.mvi.HomeAction
import com.henriquevieira.notes.features.home.mvi.HomeResult
import com.henriquevieira.notes.features.home.mvi.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val noteUseCase: NoteUseCase,
) : BaseViewModel<HomeAction, HomeResult, HomeState>() {

    private val list = mutableStateListOf<Note>()

    private val alertDialogState = mutableStateOf(false)

    override val initialState: HomeState
        get() = HomeState()

    override fun dispatch(action: HomeAction) {
        when (action) {
            is HomeAction.CardClick -> onCardClick(action.noteId)
            is HomeAction.AddClick -> onAddClick()
            is HomeAction.ListClick -> onListClick()
            is HomeAction.CloseClick -> onCloseClick()
            is HomeAction.CardLongPress -> showDeleteDialog(action.note)
            is HomeAction.DeleteConfirm -> onDeleteNote(action.note)
            is HomeAction.FetchData -> fetchData()
        }
    }

    private fun fetchData() = viewModelScope.launch(Dispatchers.IO) {
        try {
            list.clear()
            noteUseCase.getNotes().collect {
                list.addAll(it)
            }

            updateUiState(
                uiState.value.copy(
                    notesList = list,
                )
            )

        } catch (e: Exception) {
            e.printStackTrace()
            emitResult(HomeResult.OnFetchError)
        }
    }

    private fun onCloseClick() = viewModelScope.launch {
        emitResult(HomeResult.OnCloseClick)
    }
    private fun onAddClick() = viewModelScope.launch {
        emitResult(HomeResult.OnAddClick)
    }

    private fun onListClick() = viewModelScope.launch {
        emitResult(HomeResult.OnListClick)
    }

    private fun showDeleteDialog(note: Note) = viewModelScope.launch {
        alertDialogState.value = true

        updateUiState(
            uiState.value.copy(
                alertDialogState = alertDialogState.value
            )
        )

        emitResult(HomeResult.OnShowAlertDialog(note))
    }

    private fun onDeleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        try {
            noteUseCase.deleteNote(note)
            emitResult(HomeResult.OnDeleteSuccess)
        } catch (e: Exception) {
            emitResult(HomeResult.OnDeleteError)
        }
    }

    private fun onCardClick(noteId: Int) = viewModelScope.launch {
        emitResult(HomeResult.OnCardClick(noteId))
    }
}