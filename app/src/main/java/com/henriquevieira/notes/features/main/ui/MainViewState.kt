package com.henriquevieira.notes.features.main.ui

import com.henriquevieira.commonsui.textinput.CustomInputType

data class MainViewState(
    val noteColor: CustomInputType = CustomInputType.Primary,
    val errorMessage: String = ""
)