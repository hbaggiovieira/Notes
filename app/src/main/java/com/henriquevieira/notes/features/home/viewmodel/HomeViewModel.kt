package com.henriquevieira.notes.features.home.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.henriquevieira.notes.data.model.Note
import com.henriquevieira.notes.data.room.AppDatabase
import com.henriquevieira.notes.features.home.ui.HomeEvents
import com.henriquevieira.notes.features.home.ui.HomeScreenStates
import com.henriquevieira.notes.features.home.ui.HomeViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val appDatabase: AppDatabase,
) : ViewModel() {

    private val _screen = MutableSharedFlow<HomeScreenStates>()
    val screen: SharedFlow<HomeScreenStates> = _screen

    private val _uiState = MutableStateFlow(HomeViewState())
    val uiState = _uiState.asStateFlow()

    private val list = mutableStateListOf<Note>()

    private val alertDialogState = mutableStateOf(false)

    fun dispatch(event: HomeEvents) = viewModelScope.launch {
        when (event) {
            is HomeEvents.CardClick -> {
                onCardClick(event.noteId)
            }
            is HomeEvents.AddClick -> {
                onAddClick()
            }
            is HomeEvents.CardLongPress -> {
                showDeleteDialog(event.note)
            }
            is HomeEvents.DeleteConfirm -> {
                onDeleteNote(event.note)
            }
            is HomeEvents.FetchData -> {
                fetchData()
            }
        }
    }

    private fun fetchData() = viewModelScope.launch(Dispatchers.IO) {
        list.clear()
        list.addAll(appDatabase.noteDao().getAll())

        launch(Dispatchers.Main) {
            _uiState.value = _uiState.value.copy(
                notesList = list,
            )

            _screen.emit(HomeScreenStates.OnFetchSuccess)
        }
    }

    private fun onAddClick() = viewModelScope.launch {
        _screen.emit(HomeScreenStates.OnAddClick)
    }

    private fun showDeleteDialog(note: Note) = viewModelScope.launch {
        alertDialogState.value = true

        _uiState.value = _uiState.value.copy(
            alertDialogState = alertDialogState.value
        )

        _screen.emit(HomeScreenStates.OnShowAlertDialog(note))
    }

    private fun onDeleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        try {
            appDatabase.noteDao().delete(note)

            _screen.emit(HomeScreenStates.OnDeleteSuccess)
        } catch (e: Exception) {
            _screen.emit(HomeScreenStates.OnDeleteError)
        }
    }

    private fun onCardClick(noteId: Int) = viewModelScope.launch {
        _screen.emit(HomeScreenStates.OnCardClick(noteId))
    }
}