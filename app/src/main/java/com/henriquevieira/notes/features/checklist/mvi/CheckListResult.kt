package com.henriquevieira.notes.features.checklist.mvi

sealed class CheckListResult {
    object OnClose: CheckListResult()
    object OnOpenAddItem: CheckListResult()
    data class OnError(val message: String = ""): CheckListResult()
    object OnSaveSuccess: CheckListResult()
}