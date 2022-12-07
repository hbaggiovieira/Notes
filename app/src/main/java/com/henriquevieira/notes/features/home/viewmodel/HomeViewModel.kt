package com.henriquevieira.notes.features.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.henriquevieira.commonsui.textinput.NoteTypes
import com.henriquevieira.notes.features.home.model.NoteModel
import com.henriquevieira.notes.features.home.ui.HomeEvents
import com.henriquevieira.notes.features.home.ui.HomeScreenStates
import com.henriquevieira.notes.features.home.ui.HomeViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

) : ViewModel() {

    private val _screen = MutableSharedFlow<HomeScreenStates>()
    val screen: SharedFlow<HomeScreenStates> = _screen

    private val _uiState = MutableStateFlow(HomeViewState())
    val uiState = _uiState.asStateFlow()

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

    fun onCreate() {
        _uiState.value = _uiState.value.copy(
            notesList = listOf(
                NoteModel(
                    title = "A",
                    contentText = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
                    noteType = NoteTypes.Primary
                ),
                NoteModel(
                    title = "B",
                    contentText = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBB",
                    noteType = NoteTypes.Red
                ),
                NoteModel(
                    title = "C",
                    contentText = "CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC",
                    noteType = NoteTypes.Green
                ),
                NoteModel(
                    title = "D",
                    contentText = "DDDDDDDDDDDDDDDDDDDDDDDDDDDDD",
                    noteType = NoteTypes.Yellow
                ),
                NoteModel(
                    title = "E",
                    contentText = "EEEEEEEEEEEEEEEEEEEEEEEEEEEEE",
                    noteType = NoteTypes.Blue
                ),
            )
        )
    }

    private fun onAddClick() {

    }

    private fun onCardClick(selectedNote: NoteModel) = viewModelScope.launch {
        _screen.emit(HomeScreenStates.OnCardClick(selectedNote))
    }
}