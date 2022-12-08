package com.henriquevieira.notes.features.home.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.henriquevieira.commonsui.textinput.NoteTypes
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

    init {
        viewModelScope.launch(Dispatchers.IO) {
            list.addAll(appDatabase.noteDao().getAll())
        }
    }

    fun dispatch(event: HomeEvents) = viewModelScope.launch {
        when (event) {
            is HomeEvents.OnCardClick -> {
                onCardClick(event.selectedNote)
            }
            is HomeEvents.OnAddClick -> {
                onAddClick()
            }
        }
    }

    fun onCreate() = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(
            notesList = list,
        )

        _screen.emit(HomeScreenStates.OnFetchSuccess)
    }

    private fun onAddClick() = viewModelScope.launch {
        _screen.emit(HomeScreenStates.OnAddClick)
    }

    private fun onCardClick(selectedNote: Note) = viewModelScope.launch {
        _screen.emit(HomeScreenStates.OnCardClick(selectedNote))
    }
}