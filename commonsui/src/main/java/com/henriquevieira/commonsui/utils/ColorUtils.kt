package com.henriquevieira.commonsui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.henriquevieira.commonsui.ds.*
import com.henriquevieira.commonsui.textinput.NoteTypes

class ColorUtils {
    companion object {
        @Composable
        fun getBackgroundColorByType(noteType: NoteTypes) = when (noteType) {
            NoteTypes.Primary -> {
                color_card_default
            }
            NoteTypes.Red -> {
                color_card_red
            }
            NoteTypes.Green -> {
                color_card_green
            }
            NoteTypes.Yellow -> {
                color_card_yellow
            }
            NoteTypes.Blue -> {
                color_card_blue
            }
        }

        @Composable
        fun getContentColorByType(noteType: NoteTypes) = when (noteType) {
            NoteTypes.Red -> {
                Color.White
            }
            NoteTypes.Blue -> {
                Color.White
            }
            else -> {
                Color.Black
            }
        }
    }
}