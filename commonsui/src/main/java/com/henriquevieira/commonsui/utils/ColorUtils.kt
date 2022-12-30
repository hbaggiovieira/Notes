package com.henriquevieira.commonsui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.henriquevieira.commonsui.ds.*
import com.henriquevieira.commonsui.textinput.NoteType
import com.henriquevieira.commonsui.textinput.NoteType.*

class ColorUtils {
    companion object {
        @Composable
        fun getBackgroundColorByType(noteType: NoteType) = when (noteType) {
            Primary -> {
                color_card_default
            }
            Red -> {
                color_card_red
            }
            Green -> {
                color_card_green
            }
            Yellow -> {
                color_card_yellow
            }
            Blue -> {
                color_card_blue
            }
        }

        @Composable
        fun getContentColorByType(noteType: NoteType) = when (noteType) {
            Red -> {
                Color.White
            }
            Blue -> {
                Color.White
            }
            else -> {
                Color.Black
            }
        }
    }
}