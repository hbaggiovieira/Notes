package com.henriquevieira.notes.features.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.henriquevieira.commonsui.textinput.NoteTypes
import com.henriquevieira.notes.data.CustomSharedPreferences
import com.henriquevieira.notes.data.CustomSharedPreferencesKeys
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
            is MainScreenEvent.OnPrimaryColorSelected -> {
                changeColorState(NoteTypes.Primary)
                _screen.emit(MainScreenEvent.OnPrimaryColorSelected)
            }
            is MainScreenEvent.OnRedColorSelected -> {
                changeColorState(NoteTypes.Red)
                _screen.emit(MainScreenEvent.OnRedColorSelected)
            }
            is MainScreenEvent.OnGreenColorSelected -> {
                changeColorState(NoteTypes.Green)
                _screen.emit(MainScreenEvent.OnGreenColorSelected)
            }
            is MainScreenEvent.OnYellowColorSelected -> {
                changeColorState(NoteTypes.Yellow)
                _screen.emit(MainScreenEvent.OnYellowColorSelected)
            }
            is MainScreenEvent.OnBlueColorSelected -> {
                changeColorState(NoteTypes.Blue)
                _screen.emit(MainScreenEvent.OnBlueColorSelected)
            }
            is MainScreenEvent.OnClickClearButton -> {
                _uiState.value = _uiState.value.copy(
                    contentText = CONTENT_TEXT_DEFAULT
                )
            }
            is MainScreenEvent.OnClickSaveButton -> {
                onClickSaveButton(event.contentText)
                _screen.emit(MainScreenEvent.OnClickSaveButton(event.contentText))
            }
            is MainScreenEvent.OnSaveSuccess -> {

            }
            is MainScreenEvent.OnSaveError -> {

            }
        }
    }

    fun onCreate() {
        handleSavedColor()
        handleSavedContentText()
    }

    private fun onClickSaveButton(contentText: String) = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(
            contentText = contentText
        )

        try {
            customSharedPreferences.putString(CustomSharedPreferencesKeys.SELECTED_COLOR,
                uiState.value.noteType.toString())

            customSharedPreferences.putString(CustomSharedPreferencesKeys.CONTENT_TEXT,
                uiState.value.contentText)

            _screen.emit(MainScreenEvent.OnSaveSuccess)
        } catch (e: Exception) {
            _screen.emit(MainScreenEvent.OnSaveError)
        }
    }

    private fun handleSavedColor() = viewModelScope.launch {
        val savedColor =
            customSharedPreferences.getString(CustomSharedPreferencesKeys.SELECTED_COLOR,
                SELECTED_COLOR_DEFAULT)

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
        val savedContentText =
            customSharedPreferences.getString(CustomSharedPreferencesKeys.CONTENT_TEXT,
                CONTENT_TEXT_DEFAULT)

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
        private const val SELECTED_COLOR_DEFAULT = "Primary"
        private const val CONTENT_TEXT_DEFAULT = ""
    }
}