package com.henriquevieira.commonsui.utils

import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver

val NoteType = SemanticsPropertyKey<String>("NoteType")
var SemanticsPropertyReceiver.noteType by NoteType