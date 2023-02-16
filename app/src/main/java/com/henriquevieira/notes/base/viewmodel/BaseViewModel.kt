package com.henriquevieira.notes.base.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel <A, R, S> : ViewModel(){
    private val _screen = MutableSharedFlow<R>()
    val screen: SharedFlow<R> = _screen

    private val _uiState: MutableStateFlow<S> by lazy { MutableStateFlow(initialState) }
    val uiState: StateFlow<S> = _uiState

    abstract val initialState: S

    abstract fun dispatch(action: A)

    protected fun emitResult(screenResult: R) = viewModelScope.launch {
        _screen.emit(screenResult)
    }

    protected fun updateUiState(uiState: S) {
        _uiState.value = uiState
    }
}