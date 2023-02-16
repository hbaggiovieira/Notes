package com.henriquevieira.notes.features.checklist.mvi

sealed class CheckListResult {
    object OnCloseButtonClick: CheckListResult()
    data class OnLoadingChanged(val isLoading: Boolean): CheckListResult()
    object OnClickAddItem: CheckListResult()
    data class OnError(val message: String = ""): CheckListResult()
}
