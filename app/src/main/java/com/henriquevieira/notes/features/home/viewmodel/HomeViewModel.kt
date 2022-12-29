package com.henriquevieira.notes.features.home.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.henriquevieira.notes.base.BaseViewModel
import com.henriquevieira.notes.data.model.Note
import com.henriquevieira.notes.domain.NoteUseCase
import com.henriquevieira.notes.features.home.ui.HomeActions
import com.henriquevieira.notes.features.home.ui.HomeResults
import com.henriquevieira.notes.features.home.ui.HomeStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val noteUseCase: NoteUseCase,
) : BaseViewModel<HomeActions, HomeResults, HomeStates>() {

    private val list = mutableStateListOf<Note>()

    private val alertDialogState = mutableStateOf(false)

    private fun fetchData() = viewModelScope.launch(Dispatchers.IO) {
        try {
            list.clear()
            noteUseCase.getNotes().collect {
                list.addAll(it)
            }

            updateUiState(uiState.value.copy(
                notesList = list,
            ))

        } catch (e: Exception) {
            emitResult(HomeResults.OnFetchError)
        }
    }

    private fun onAddClick() = viewModelScope.launch {
        emitResult(HomeResults.OnAddClick)
    }

    private fun showDeleteDialog(note: Note) = viewModelScope.launch {
        alertDialogState.value = true

        updateUiState(uiState.value.copy(
            alertDialogState = alertDialogState.value
        ))

        emitResult(HomeResults.OnShowAlertDialog(note))
    }

    private fun onDeleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        try {
            noteUseCase.deleteNote(note)
            emitResult(HomeResults.OnDeleteSuccess)
        } catch (e: Exception) {
            emitResult(HomeResults.OnDeleteError)
        }
    }

    private fun onCardClick(noteId: Int) = viewModelScope.launch {
        emitResult(HomeResults.OnCardClick(noteId))
    }

    override val initialState: HomeStates
        get() = HomeStates()

    override fun dispatch(action: HomeActions) {
        when (action) {
            is HomeActions.CardClick -> {
                onCardClick(action.noteId)
            }
            is HomeActions.AddClick -> {
                onAddClick()
            }
            is HomeActions.CardLongPress -> {
                showDeleteDialog(action.note)
            }
            is HomeActions.DeleteConfirm -> {
                onDeleteNote(action.note)
            }
            is HomeActions.FetchData -> {
                fetchData()
            }
        }
    }
}