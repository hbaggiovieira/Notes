package com.henriquevieira.notes.features.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.henriquevieira.commonsui.textinput.CustomInputType
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
            is MainScreenEvent.OnSuccess -> {
                handleColor(CustomInputType.Green)
            }
            is MainScreenEvent.OnError -> {
                handleColor(CustomInputType.Red)
            }
            is MainScreenEvent.OnPrimaryColorSelected -> {
                handleColor(CustomInputType.Primary)
            }
            is MainScreenEvent.OnRedColorSelected -> {
                handleColor(CustomInputType.Red)
            }
            is MainScreenEvent.OnGreenColorSelected -> {
                handleColor(CustomInputType.Green)
            }
            is MainScreenEvent.OnYellowColorSelected -> {
                handleColor(CustomInputType.Yellow)
            }
            is MainScreenEvent.OnBlueColorSelected -> {
                handleColor(CustomInputType.Blue)
            }
        }
    }

    private fun handleColor(customInputType: CustomInputType) = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(
            noteColor = customInputType
        )
    }

}