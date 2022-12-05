package com.henriquevieira.notes.features.main.viewmodel

import androidx.lifecycle.ViewModel
import com.henriquevieira.notes.data.CustomSharedPreferences
import com.henriquevieira.notes.features.main.ui.MainViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel
    @Inject constructor(
        private val customSharedPreferences: CustomSharedPreferences
    )
    : ViewModel() {

    private val _screen = MutableSharedFlow<MainScreenEvent>()
    val screen: SharedFlow<MainScreenEvent> = _screen

    private val _uiState = MutableStateFlow(MainViewState())
    val uiState = _uiState.asStateFlow()

    fun dispatch(event: MainScreenEvent) {
        when (event) {
            is MainScreenEvent.OnSuccess -> {

            }
            is MainScreenEvent.OnError -> {

            }
        }
    }

}