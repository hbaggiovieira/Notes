package com.henriquevieira.notes.utils

import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assert
import com.henriquevieira.commonsui.utils.NoteType

fun SemanticsNodeInteraction.assertNoteType(noteTypeId: String): SemanticsNodeInteraction =
    assert(SemanticsMatcher.expectValue(NoteType, noteTypeId))