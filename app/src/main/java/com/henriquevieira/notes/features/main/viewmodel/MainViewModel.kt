package com.henriquevieira.notes.features.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.henriquevieira.commonsui.textinput.NoteTypes
import com.henriquevieira.notes.data.CustomSharedPreferences
import com.henriquevieira.notes.features.main.ui.MainViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
    private val customSharedPreferences: CustomSharedPreferences,
) : ViewModel() {

    private val _screen = MutableSharedFlow<MainScreenEvent>()
    val screen: SharedFlow<MainScreenEvent> = _screen

    private val _uiState = MutableStateFlow(MainViewState())
    val uiState = _uiState.asStateFlow()

    fun dispatch(event: MainScreenEvent) = viewModelScope.launch {
        when (event) {
            is MainScreenEvent.OnCreate -> {
                handleSavedColor()
                handleSavedContentText()
            }
            is MainScreenEvent.OnPrimaryColorSelected -> {
                changeColorState(NoteTypes.Primary)
            }
            is MainScreenEvent.OnRedColorSelected -> {
                changeColorState(NoteTypes.Red)
            }
            is MainScreenEvent.OnGreenColorSelected -> {
                changeColorState(NoteTypes.Green)
            }
            is MainScreenEvent.OnYellowColorSelected -> {
                changeColorState(NoteTypes.Yellow)
            }
            is MainScreenEvent.OnBlueColorSelected -> {
                changeColorState(NoteTypes.Blue)
            }
            is MainScreenEvent.OnClickSaveButton -> {
                onClickSaveButton(event.contentText)
            }
            is MainScreenEvent.OnSaveSuccess -> {

            }
            is MainScreenEvent.OnSaveError -> {

            }
        }
    }

    private fun onClickSaveButton(contentText: String) = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(
            contentText = contentText
        )

        try {
            customSharedPreferences.putString(SELECTED_COLOR_KEY,
                uiState.value.noteType.toString())
            customSharedPreferences.putString(CONTENT_TEXT_KEY, uiState.value.contentText)
            _screen.emit(MainScreenEvent.OnSaveSuccess)
        } catch (e: Exception) {
            _screen.emit(MainScreenEvent.OnSaveError)
        }
    }

    private fun handleSavedColor() = viewModelScope.launch {
        val savedColor =
            customSharedPreferences.getString(SELECTED_COLOR_KEY, SELECTED_COLOR_DEFAULT)
        _uiState.value = _uiState.value.copy(
            noteType = when (savedColor) {
                NoteTypes.Primary.toString() -> NoteTypes.Primary
                NoteTypes.Red.toString() -> NoteTypes.Red
                NoteTypes.Green.toString() -> NoteTypes.Green
                NoteTypes.Yellow.toString() -> NoteTypes.Yellow
                NoteTypes.Blue.toString() -> NoteTypes.Blue
                else -> NoteTypes.Primary
            }
        )
    }

    private fun handleSavedContentText() = viewModelScope.launch {
        val savedContentText = customSharedPreferences.getString(CONTENT_TEXT_KEY, CONTENT_TEXT_DEFAULT)
        _uiState.value = _uiState.value.copy(
            contentText = savedContentText
        )
    }

    private fun changeColorState(noteTypes: NoteTypes) = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(
            noteType = noteTypes
        )
    }

    companion object {
        private const val SELECTED_COLOR_KEY = "SELECTED_COLOR_KEY"
        private const val SELECTED_COLOR_DEFAULT = "Primary"

        private const val CONTENT_TEXT_KEY = "CONTENT_TEXT_KEY"
        private const val CONTENT_TEXT_DEFAULT = "CONTENT_TEXT_DEFAULT"
    }
}