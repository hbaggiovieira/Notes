package com.henriquevieira.notes.features.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.henriquevieira.commonsui.textinput.NoteTypes
import com.henriquevieira.notes.data.CustomSharedPreferences
import com.henriquevieira.notes.data.CustomSharedPreferencesKeys
import com.henriquevieira.notes.features.home.model.NoteModel
import com.henriquevieira.notes.features.main.ui.MainEvents
import com.henriquevieira.notes.features.main.ui.MainScreenStates
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

    private val _screen = MutableSharedFlow<MainScreenStates>()
    val screen: SharedFlow<MainScreenStates> = _screen

    private val _uiState = MutableStateFlow(MainViewState())
    val uiState = _uiState.asStateFlow()

    fun onCreate() {
//        handleSavedColor()
//        handleSavedContentText()
    }

    fun dispatch(event: MainEvents) = viewModelScope.launch {
        when (event) {
            is MainEvents.OnPrimaryColorSelected -> {
                changeColorState(NoteTypes.Primary)
            }
            is MainEvents.OnRedColorSelected -> {
                changeColorState(NoteTypes.Red)
            }
            is MainEvents.OnGreenColorSelected -> {
                changeColorState(NoteTypes.Green)
            }
            is MainEvents.OnYellowColorSelected -> {
                changeColorState(NoteTypes.Yellow)
            }
            is MainEvents.OnBlueColorSelected -> {
                changeColorState(NoteTypes.Blue)
            }
            is MainEvents.OnClickSaveButton -> {
                onClickSaveButton(event.contentText)
            }
        }
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

            _screen.emit(MainScreenStates.OnSaveSuccess)
        } catch (e: Exception) {
            _screen.emit(MainScreenStates.OnSaveError)
        }
    }

    private fun getSelectedNote() {

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

    fun loadSelectedNote(note: NoteModel) {
        _uiState.value = _uiState.value.copy(
            noteModel = note,
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