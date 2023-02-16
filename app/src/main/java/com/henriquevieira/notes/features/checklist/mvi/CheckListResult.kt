package com.henriquevieira.notes.features.checklist.mvi

sealed class CheckListResult {
    object OnCloseButtonClick: CheckListResult()
    data class OnLoadingChanged(val isLoading: Boolean): CheckListResult()
}
