package com.henriquevieira.commonsui.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.henriquevieira.commonsui.textinput.NoteTypes

class ColorUtils {
    companion object {
        @Composable
        fun getBackgroundColorByType(noteType: NoteTypes) = when (noteType) {
            NoteTypes.Primary -> {
                MaterialTheme.colorScheme.primaryContainer
            }
            NoteTypes.Red -> {
                Color.Red
            }
            NoteTypes.Green -> {
                Color.Green
            }
            NoteTypes.Yellow -> {
                Color.Yellow
            }
            NoteTypes.Blue -> {
                Color.Blue
            }
        }

        @Composable
        fun getContentColorByType(noteType: NoteTypes) = when (noteType) {
            NoteTypes.Primary -> {
                Color.Black
            }
            NoteTypes.Red -> {
                Color.White
            }
            NoteTypes.Green -> {
                Color.Black
            }
            NoteTypes.Yellow -> {
                Color.Black
            }
            NoteTypes.Blue -> {
                Color.White
            }
        }
    }
}